package com.daeliin.components.cms.article;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.event.EventLogService;
import com.daeliin.components.cms.sql.BArticle;
import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.resource.Id;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class ArticleService  {

    private final ArticleRepository repository;
    private final ArticleConversion conversion;
    private final AccountService accountService;
    private final EventLogService eventLogService;

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
            throw new NoSuchElementException(String.format("Account %s doesn't exist", article.author));
        }

        Article articleToCreate = new Article(
                new Id().value,
                Instant.now(),
                article.author,
                article.title,
                article.urlFriendlyTitle,
                article.description,
                article.content,
                article.source,
                null,
                false);

        Article createdArticle = instantiate(repository.save(map(articleToCreate, author.getId())), author.username);

        eventLogService.create(String.format("The article %s has been created", createdArticle.title));

        return createdArticle;
    }

    public Article update(String articleId, Article article) {
        BArticle existingArticle = repository.findOne(articleId).orElseThrow(() ->
                new NoSuchElementException(String.format("Article %s doesn't exist", articleId)));

        if (existingArticle.getPublished()) {
            throw new IllegalStateException(String.format("Article %s is published, it can't be updated", articleId));
        }

        Account author = accountService.findOne(existingArticle.getAuthorId());

        existingArticle.setTitle(article.title);
        existingArticle.setUrlFriendlyTitle(new UrlFriendlyString(article.title).value);
        existingArticle.setDescription(article.description);
        existingArticle.setContent(article.content);
        existingArticle.setSource(article.source);

        return  instantiate(repository.save(existingArticle), author.username);
    }

    public Article findOne(String articleId) {
        BArticle bArticle = repository.findOne(articleId).orElseThrow(() ->
                new NoSuchElementException(String.format("Article %s doesn't exist", articleId)));

        Account author = accountService.findOne(bArticle.getAuthorId());

        return instantiate(bArticle, author.username);
    }

    public Page<Article> findAll(PageRequest pageRequest) {
        Page<BArticle> articlePage = repository.findAll(pageRequest);

        return new Page<>(instantiate(articlePage.items), articlePage.totalItems, articlePage.totalPages);
    }

    public Page<Article> findAll(Predicate predicate, PageRequest pageRequest) {
        Page<BArticle> articlePage = repository.findAll(predicate, pageRequest);

        return new Page<>(instantiate(articlePage.items), articlePage.totalItems, articlePage.totalPages);
    }

    public boolean exists(String articleId) {
        return repository.exists(articleId);
    }

    public boolean delete(String id) {
        Article article = findOne(id);

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
        BArticle existingArticle = repository.findOne(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Article %s doesn't exist", id)));

        Account author = accountService.findOne(existingArticle.getAuthorId());

        existingArticle.setPublished(published);
        existingArticle.setPublicationDate(published ? publicationDate : null);

        return instantiate(repository.save(existingArticle), author.username);
    }
}
