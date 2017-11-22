package com.daeliin.components.cms.news;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.event.EventLogService;
import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final NewsConversion conversion;
    private final AccountService accountService;
    private final EventLogService eventLogService;

    @Inject
    public NewsService(NewsRepository repository, AccountService accountService, EventLogService eventLogService) {
        this.repository = repository;
        this.conversion = new NewsConversion();
        this.accountService = accountService;
        this.eventLogService = eventLogService;
    }

    public News create(News news) {
        Account author = accountService.findByUsernameAndEnabled(news.author);

        if (author == null) {
            throw new NoSuchElementException(String.format("Account %s doesn't exist", news.author));
        }

        News newsToCreate = new News(UUID.randomUUID().toString(), Instant.now(), news.author, news.content, news.source, null, false);
        News createdNews = instantiate(repository.save(conversion.map(newsToCreate, author.getId())), author.username);

        eventLogService.create(String.format("A news has been added : %s", createdNews.content));

        return createdNews;
    }

    public News update(String newsId, News news) {
        BNews existingNews = repository.findOne(newsId).orElseThrow(() ->
                new NoSuchElementException(String.format("News %s doesn't exist", newsId)));

        if (existingNews.getPublished()) {
            throw new IllegalStateException(String.format("News %s is published, it can't be updated", newsId));
        }

        Account author = accountService.findOne(existingNews.getAuthorId());

        existingNews.setContent(news.content);
        existingNews.setSource(news.source);

        return  instantiate(repository.save(existingNews), author.username);
    }

    public News findOne(String id) {
        BNews existingNews = repository.findOne(id).orElseThrow(() ->
                new NoSuchElementException(String.format("News %s doesn't exist", id)));

        Account author = accountService.findOne(existingNews.getAuthorId());

        return instantiate(existingNews, author.username);
    }

    public Page<News> findAll(PageRequest pageRequest) {
        Page<BNews> newsPage = repository.findAll(pageRequest);

        return new Page<>(instantiate(newsPage.items), newsPage.totalItems, newsPage.totalPages);
    }

    public Page<News> findAll(Predicate predicate, PageRequest pageRequest) {
        Page<BNews> newsPage = repository.findAll(predicate, pageRequest);

        return new Page<>(instantiate(newsPage.items), newsPage.totalItems, newsPage.totalPages);
    }

    public boolean exists(String id) {
        return repository.exists(id);
    }

    public boolean delete(String id) {
        if (!exists(id)) {
            throw new NoSuchElementException(String.format("News %s doesn't exist", id));
        }

        News newsToDelete = findOne(id);

        boolean deleted = repository.delete(id);

        eventLogService.create(String.format("The news has been deleted", newsToDelete.content));

        return deleted;
    }

    public News publish(String id) {
        News updatedNews = updatePublication(id, true, Instant.now());

        eventLogService.create(String.format("The news %s has been published", updatedNews.content));

        return updatedNews;
    }

    public News unpublish(String id) {
        News updatedNews = updatePublication(id, false, null);

        eventLogService.create(String.format("The news %s has been unpublished", updatedNews.content));

        return updatedNews;
    }

    private News updatePublication(String id, boolean published, Instant publicationDate) {
        BNews existingNews = repository.findOne(id).orElseThrow(() ->
                new NoSuchElementException(String.format("News %s doesn't exist", id)));

        Account author = accountService.findOne(existingNews.getAuthorId());

        existingNews.setPublished(published);
        existingNews.setPublicationDate(published ? publicationDate : null);

        return instantiate(repository.save(existingNews), author.username);
    }

    private News instantiate(BNews bNews, String author) {
        return conversion.instantiate(bNews, author);
    }

    private Set<News> instantiate(Collection<BNews> bNewsCollection) {
        Map<String, Account> accountByIds = new HashMap<>();
        Set<String> authorIds = bNewsCollection.stream().map(BNews::getAuthorId).collect(toSet());

        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.getId(), account));

        return bNewsCollection
                .stream()
                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
                .collect(toCollection(TreeSet::new));
    }
}
