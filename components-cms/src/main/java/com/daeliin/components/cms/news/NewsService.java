package com.daeliin.components.cms.news;

import com.daeliin.components.cms.article.Article;
import com.daeliin.components.cms.article.ArticleService;
import com.daeliin.components.persistence.event.EventLogService;
import com.daeliin.components.persistence.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.sql.BNews;
import com.daeliin.components.core.sql.QNews;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final NewsConversion conversion;
    private final AccountService accountService;
    private final EventLogService eventLogService;

    @Inject
    private ArticleService articleService;

    @Inject
    public NewsService(NewsRepository repository, AccountService accountService, EventLogService eventLogService) {
        this.repository = repository;
        this.conversion = new NewsConversion();
        this.accountService = accountService;
        this.eventLogService = eventLogService;
    }

    public News create(String articleId, News news) {
        Article article = articleService.findOne(articleId);
        Account author = accountService.findByUsernameAndEnabled(news.author);

        if (article == null) {
            throw new PersistentResourceNotFoundException(String.format("Article %s doesn't exist", articleId));
        }

        if (article.published) {
            throw new IllegalStateException(String.format("Article %s is published, can't add news to it", article));
        }

        if (author == null) {
            throw new PersistentResourceNotFoundException(String.format("Account %s doesn't exist", news.author));
        }

        News newsToCreate = new News(UUID.randomUUID().toString(), Instant.now(), news.author, news.content, news.source);

        News createdNews = instantiate(repository.save(map(newsToCreate, articleId, author.getId())), author.username);

        eventLogService.create(String.format("A news has been added for article %s", article.title));

        return createdNews;
    }

    public News update(String newsId, News news) {
        BNews existingNews = repository.findOne(newsId);

        if (existingNews == null) {
            throw new PersistentResourceNotFoundException(String.format("News %s doesn't exist", newsId));
        }

        Account author = accountService.findOne(existingNews.getAuthorId());

        existingNews.setContent(news.content);
        existingNews.setSource(news.source);

        return  instantiate(repository.save(existingNews), author.username);
    }

    public Collection<News> findForArticle(String articleId) {
        if (!articleService.exists(articleId)) {
            throw new PersistentResourceNotFoundException(String.format("Article %s doesn't exist", articleId));
        }

        PageRequest pageRequest = new PageRequest(0, 1000, Sets.newLinkedHashSet(ImmutableSet.of(new Sort("creationDate", Sort.Direction.DESC))));

        return instantiate(repository.findAll(QNews.news.articleId.eq(articleId), pageRequest).items);
    }

    public News findOne(String id) {
        BNews existingNews = repository.findOne(id);

        if (existingNews == null) {
            throw new PersistentResourceNotFoundException(String.format("News %s doesn't exist", id));
        }

        Account author = accountService.findOne(existingNews.getAuthorId());

        return instantiate(existingNews, author.username);
    }

    public boolean exists(String id) {
        return repository.exists(id);
    }

    public boolean delete(String id) {
        if (!exists(id)) {
            throw new PersistentResourceNotFoundException(String.format("News %s doesn't exist", id));
        }

        News newsToDelete = findOne(id);

        boolean deleted = repository.delete(id);

        eventLogService.create(String.format("The news has been deleted", newsToDelete.content));

        return deleted;
    }

    public boolean deleteForArticle(String articleId) {
        if (!articleService.exists(articleId)) {
            throw new PersistentResourceNotFoundException(String.format("Article %s doesn't exist", articleId));
        }

        return repository.deleteForArticle(articleId);
    }

    private BNews map(News news, String articleId, String authorId) {
        return conversion.map(news, articleId, authorId);
    }

    private News instantiate(BNews bNews, String author) {
        return conversion.instantiate(bNews, author);
    }

    private Collection<News> instantiate(Collection<BNews> bNewsCollection) {
        Map<String, Account> accountByIds = new HashMap<>();
        Set<String> authorIds = bNewsCollection.stream().map(BNews::getAuthorId).collect(toSet());

        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.getId(), account));

        return bNewsCollection
                .stream()
                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
                .collect(toCollection(TreeSet::new));
    }
}
