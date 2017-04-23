package com.daeliin.components.cms.news;

import com.daeliin.components.core.sql.BNews;

import java.sql.Timestamp;

public final class NewsConversion {

    public News instantiate(BNews bNews, String author) {
        if (bNews == null) {
            return null;
        }

        return new News(
                bNews.getId(),
                bNews.getCreationDate().toLocalDateTime(),
                author,
                bNews.getContent(),
                bNews.getSource());
    }

    public BNews map(News news, String articleId, String authorId) {
        if (news == null) {
            return null;
        }

        return new BNews(
                articleId,
                authorId,
                news.content,
                Timestamp.valueOf(news.creationDate()),
                news.id(),
                news.source);
    }
}
