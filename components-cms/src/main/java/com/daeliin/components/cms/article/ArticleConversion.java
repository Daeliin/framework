package com.daeliin.components.cms.article;

import com.daeliin.components.core.sql.BArticle;
import com.daeliin.components.core.string.UrlFriendlyString;

public final class ArticleConversion {

    public Article instantiate(BArticle bArticle, String author) {
        if (bArticle == null) {
            return null;
        }

        return new Article(
                bArticle.getId(),
                bArticle.getCreationDate(),
                author,
                bArticle.getTitle(),
                bArticle.getUrlFriendlyTitle(),
                bArticle.getDescription(),
                bArticle.getContent(),
                bArticle.getPublicationDate(),
                bArticle.getPublished());
    }

    public BArticle map(Article article, String authorId) {
        if (article == null) {
            return null;
        }

        return new BArticle(
                authorId,
                article.content,
                article.getCreationDate(),
                article.description,
                article.getId(),
                article.publicationDate,
                article.published,
                article.title,
                new UrlFriendlyString(article.title).value);
    }
}
