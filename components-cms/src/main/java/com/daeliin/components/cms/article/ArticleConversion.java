package com.daeliin.components.cms.article;

import com.daeliin.components.cms.sql.BArticle;
import com.daeliin.components.core.string.UrlFriendlyString;

public final class ArticleConversion {

    public Article instantiate(BArticle bArticle, String author) {
        return new Article(
                bArticle.getId(),
                bArticle.getCreationDate(),
                author,
                bArticle.getTitle(),
                bArticle.getUrlFriendlyTitle(),
                bArticle.getDescription(),
                bArticle.getContent(),
                bArticle.getSource(),
                bArticle.getPublicationDate(),
                bArticle.getPublished());
    }

    public BArticle map(Article article, String authorId) {
        return new BArticle(
                authorId,
                article.content,
                article.getCreationDate(),
                article.description,
                article.getId(),
                article.publicationDate,
                article.published,
                article.source,
                article.title,
                new UrlFriendlyString(article.title).value);
    }
}
