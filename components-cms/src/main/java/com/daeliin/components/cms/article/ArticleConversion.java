package com.daeliin.components.cms.article;

import com.daeliin.components.core.sql.BArticle;
import com.daeliin.components.domain.utils.UrlFriendlyString;

import java.sql.Timestamp;

public final class ArticleConversion {

    public Article instantiate(BArticle bArticle, String author) {
        if (bArticle == null) {
            return null;
        }

        return new Article(
                bArticle.getId(),
                bArticle.getCreationDate().toLocalDateTime(),
                author,
                bArticle.getTitle(),
                bArticle.getUrlFriendlyTitle(),
                bArticle.getDescription(),
                bArticle.getContent(),
                bArticle.getPublicationDate() != null ? bArticle.getPublicationDate().toLocalDateTime() : null,
                bArticle.getPublished());
    }

    public BArticle map(Article article, String authorId) {
        if (article == null) {
            return null;
        }

        return new BArticle(
                authorId,
                article.content,
                Timestamp.valueOf(article.getCreationDate()),
                article.description,
                article.getId(),
                article.publicationDate != null ? Timestamp.valueOf(article.publicationDate) : null,
                article.published,
                article.title,
                new UrlFriendlyString(article.title).value);
    }
}
