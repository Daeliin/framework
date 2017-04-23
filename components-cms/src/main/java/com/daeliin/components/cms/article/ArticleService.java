package com.daeliin.components.cms.article;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.sql.BArticle;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.utils.UrlFriendlyString;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class ArticleService  {

    private final ArticleRepository repository;
    private final ArticleConversion conversion;
    private final AccountService accountService;

    @Inject
    public ArticleService(ArticleRepository repository, AccountService accountService) {
        this.repository = repository;
        this.conversion = new ArticleConversion();
        this.accountService = accountService;
    }

    public Article create(Article article) {
        Account author = accountService.findByUsernameAndEnabled(article.author);

        if (author == null) {
            throw new PersistentResourceNotFoundException(String.format("Account %s doesn't exist", article.author));
        }

        Article articleToCreate = new Article(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                article.author,
                article.title,
                article.urlFriendlyTitle,
                article.description,
                article.content,
                null,
                false);

        return instantiate(repository.save(map(articleToCreate, author.id())), author.username);
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
            return null;
        }

        Account author = accountService.findOne(bArticle.getAuthorId());

        return instantiate(bArticle, author.username);
    }

    public Collection<Article> findAll(PageRequest pageRequest) {
        Page<BArticle> articlePage = repository.findAll(pageRequest);

        return null;
    }

    public boolean exists(String articleId) {
        return repository.exists(articleId);
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

        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.id(), account));

        return bArticles
                .stream()
                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
                .collect(toCollection(TreeSet::new));
    }
}
