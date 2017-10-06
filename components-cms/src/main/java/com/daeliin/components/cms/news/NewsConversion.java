package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;

import java.sql.Timestamp;

public final class NewsConversion {

    public News instantiate(BNews bNews, String author) {
        if (bNews == null) {
            return null;
        }

        return new News(
                bNews.getId(),
                bNews.getCreationDate(),
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
                news.getCreationDate(),
                news.getId(),
                news.source);
    }
}
