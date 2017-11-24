package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.core.resource.Conversion;
import com.daeliin.components.core.string.UrlFriendlyString;

public final class NewsConversion implements Conversion<News, BNews> {

    @Override
    public News instantiate(BNews bNews) {
        return new News(
                bNews.getId(),
                bNews.getCreationDate(),
                bNews.getAuthorId(),
                bNews.getTitle(),
                bNews.getUrlFriendlyTitle(),
                bNews.getDescription(),
                bNews.getContent(),
                bNews.getSource(),
                bNews.getPublicationDate(),
                bNews.getPublished());
    }

    @Override
    public BNews map(News news) {
        return new BNews(
                news.authorId,
                news.content,
                news.getCreationDate(),
                news.description,
                news.getId(),
                news.publicationDate,
                news.published,
                news.source,
                news.title,
                new UrlFriendlyString(news.title).value);
    }
}
