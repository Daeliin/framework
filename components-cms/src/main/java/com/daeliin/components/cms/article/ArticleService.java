package com.daeliin.components.cms.article;

import com.daeliin.components.cms.news.NewsService;
import com.daeliin.components.persistence.event.EventLogService;
import com.daeliin.components.persistence.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.sql.BArticle;
import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.resource.Id;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class ArticleService  {

    private final ArticleRepository repository;
    private final ArticleConversion conversion;
    private final AccountService accountService;
    private final EventLogService eventLogService;

    @Inject
    private NewsService newsService;

    @Inject
    public ArticleService(ArticleRepository repository, AccountService accountService, EventLogService eventLogService) {
        this.repository = repository;
        this.conversion = new ArticleConversion();
        this.accountService = accountService;
        this.eventLogService = eventLogService;
    }

    public Article create(Article article) {
        Account author = accountService.findByUsernameAndEnabled(article.author);

        if (author == null) {
            throw new PersistentResourceNotFoundException(String.format("Account %s doesn't exist", article.author));
        }

        Article articleToCreate = new Article(
                new Id().value,
                Instant.now(),
                article.author,
                article.title,
                article.urlFriendlyTitle,
                article.description,
                article.content,
                null,
                false);

        Article createdArticle = instantiate(repository.save(map(articleToCreate, author.getId())), author.username);

        eventLogService.create(String.format("The article %s has been created", createdArticle.title));

        return createdArticle;
    }

    public Article update(String articleId, Article article) {
        BArticle existingArticle = repository.findOne(articleId);

        if (existingArticle == null) {
            throw new PersistentResourceNotFoundException(String.format("Article %s doesn't exist", articleId));
        }

        Account author = accountService.findOne(existingArticle.getAuthorId());

        existingArticle.setTitle(article.title);
        existingArticle.setUrlFriendlyTitle(new UrlFriendlyString(article.title).value);
        existingArticle.setDescription(article.description);
        existingArticle.setContent(article.content);

        return  instantiate(repository.save(existingArticle), author.username);
    }

    public Article findOne(String articleId) {
        BArticle bArticle = repository.findOne(articleId);

        if (bArticle == null) {
            throw new PersistentResourceNotFoundException();
        }

        Account author = accountService.findOne(bArticle.getAuthorId());

        return instantiate(bArticle, author.username);
    }

    public Page<Article> findAll(PageRequest pageRequest) {
        Page<BArticle> articlePage = repository.findAll(pageRequest);

        return new Page<>(instantiate(articlePage.items), articlePage.totalItems, articlePage.totalPages);
    }

    public boolean exists(String articleId) {
        return repository.exists(articleId);
    }

    public boolean delete(String id) {
        Article article = findOne(id);

        newsService.deleteForArticle(id);

        boolean deleted = repository.delete(id);

        eventLogService.create(String.format("The article %s has been deleted", article.title));

        return deleted;
    }

    public Article publish(String id) {
        Article updatedArticle = updatePublication(id, true, Instant.now());

        eventLogService.create(String.format("The article %s has been published", updatedArticle.title));

        return updatedArticle;
    }

    public Article unpublish(String id) {
        Article updatedArticle = updatePublication(id, false, null);

        eventLogService.create(String.format("The article %s has been unpublished", updatedArticle.title));

        return updatedArticle;
    }

    private BArticle map(Article article, String authorId) {
        return conversion.map(article, authorId);
    }

    private Article instantiate(BArticle bArticle, String author) {
        return conversion.instantiate(bArticle, author);
    }

    private Collection<Article> instantiate(Collection<BArticle> bArticles) {
        Map<String, Account> accountByIds = new HashMap<>();
        Set<String> authorIds = bArticles.stream().map(BArticle::getAuthorId).collect(toSet());

        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.getId(), account));

        return bArticles
                .stream()
                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
                .collect(toCollection(LinkedHashSet::new));
    }

    private Article updatePublication(String id, boolean published, Instant publicationDate) {
        BArticle existingArticle = repository.findOne(id);

        if (existingArticle == null) {
            throw new PersistentResourceNotFoundException(String.format("Article %s doesn't exist", id));
        }

        Account author = accountService.findOne(existingArticle.getAuthorId());

        existingArticle.setPublished(published);
        existingArticle.setPublicationDate(published ? publicationDate : null);

        return instantiate(repository.save(existingArticle), author.username);
    }
}
