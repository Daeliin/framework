package com.daeliin.components.cms.news;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.event.EventLogService;
import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.cms.sql.QAccount;
import com.daeliin.components.cms.sql.QNews;
import com.daeliin.components.core.resource.Id;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
public class NewsService extends ResourceService<News, BNews, String, NewsRepository> {

    private final AccountService accountService;
    private final EventLogService eventLogService;

    @Inject
    public NewsService(NewsRepository repository, AccountService accountService, EventLogService eventLogService) {
        super(repository, new NewsConversion());
        this.accountService = accountService;
        this.eventLogService = eventLogService;
    }

    @Override
    public News create(News news) {
        Account author = accountService.findOne(news.authorId);

        if (!author.enabled) {
            throw new IllegalStateException(String.format("Account %s is not active", author.username));
        }

        News newsToCreate = new News(
                new Id().value,
                Instant.now(),
                news.authorId,
                news.title,
                news.urlFriendlyTitle,
                news.description,
                news.content,
                news.source,
                null,
                false);

        News createdNews = super.create(newsToCreate);

        eventLogService.create("A news has been created");

        return createdNews;
    }

    @Override
    public News update(News news) {
        if (news == null) {
            throw new NoSuchElementException();
        }

        BNews existingNews = repository.findOne(news.getId())
            .orElseThrow(() -> new NoSuchElementException(String.format("News %s doesn't exist", news.getId())));

        if (existingNews.getPublished()) {
            throw new IllegalStateException(String.format("News %s is published, it can't be updated", news.getId()));
        }

        existingNews.setTitle(news.title);
        existingNews.setUrlFriendlyTitle(new UrlFriendlyString(news.title).value);
        existingNews.setDescription(news.description);
        existingNews.setContent(news.content);
        existingNews.setSource(news.source);

        return conversion.instantiate(repository.save(existingNews));
    }

    @Override
    public boolean delete(String id) {
        News news = findOne(id);

        boolean deleted = repository.delete(id);

        eventLogService.create(String.format("The news %s has been deleted", news.getId()));

        return deleted;
    }

    public News publish(String id) {
        News updatedNews = updatePublication(id, true, Instant.now());

        eventLogService.create(String.format("The news %s has been published", updatedNews.getId()));

        return updatedNews;
    }

    public News unpublish(String id) {
        News updatedNews = updatePublication(id, false, null);

        eventLogService.create(String.format("The news %s has been unpublished", updatedNews.getId()));

        return updatedNews;
    }

    public Map<News, Account> authorByNews(Collection<String> newsIds) {
        Collection<News> news = findAll(QNews.news.id.in(newsIds));
        Set<String> accountIds = news.stream().map(newsItem -> newsItem.authorId).collect(toSet());
        Map<String, Account> accountById = accountService.findAll(QAccount.account.id.in(accountIds)).stream().collect(toMap(Account::getId, Function.identity()));

        return news.stream()
            .collect(toMap(Function.identity(), newsItem -> accountById.get(newsItem.authorId)));
    }

    private News updatePublication(String id, boolean published, Instant publicationDate) {
        BNews existingNews = repository.findOne(id).orElseThrow(() ->
                new NoSuchElementException(String.format("News %s doesn't exist", id)));

        existingNews.setPublished(published);
        existingNews.setPublicationDate(published ? publicationDate : null);

        return conversion.instantiate(repository.save(existingNews));
    }
}
