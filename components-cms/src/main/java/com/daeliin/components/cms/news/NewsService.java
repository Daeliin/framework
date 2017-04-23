package com.daeliin.components.cms.news;

import com.daeliin.components.cms.article.Article;
import com.daeliin.components.cms.article.ArticleService;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.sql.BNews;
import com.daeliin.components.core.sql.QNews;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

@Service
public class NewsService {

    private final NewsRepository repository;
    private final NewsConversion conversion;
    private final AccountService accountService;
    private final ArticleService articleService;

    @Inject
    public NewsService(NewsRepository repository, AccountService accountService, ArticleService articleService) {
        this.repository = repository;
        this.conversion = new NewsConversion();
        this.accountService = accountService;
        this.articleService = articleService;
    }

    public News create(News news, String articleId) {
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

        return instantiate(repository.save(map(news, articleId, author.id())), author.username);
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

        return instantiate(repository.findAll(QNews.news.articleId.eq(articleId)));
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

        accountService.findAll(authorIds).forEach(account -> accountByIds.put(account.id(), account));

        return bNewsCollection
                .stream()
                .map(bNews -> instantiate(bNews, accountByIds.get(bNews.getAuthorId()).username))
                .collect(toCollection(TreeSet::new));
    }
}
