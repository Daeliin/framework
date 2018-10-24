package com.blebail.components.cms.news;

import com.blebail.components.cms.publication.PublicationStatus;
import com.blebail.components.cms.sql.BNews;
import com.blebail.components.core.resource.Conversion;
import com.blebail.components.core.string.UrlFriendlyString;

public final class NewsConversion implements Conversion<News, BNews> {

    @Override
    public News from(BNews bNews) {
        return new News(
                bNews.getId(),
                bNews.getCreationDate(),
                bNews.getAuthorId(),
                bNews.getTitle(),
                bNews.getUrlFriendlyTitle(),
                bNews.getDescription(),
                bNews.getContent(),
                bNews.getRenderedContent(),
                bNews.getSource(),
                bNews.getPublicationDate(),
                PublicationStatus.valueOf(bNews.getStatus()));
    }

    @Override
    public BNews to(News news) {
        return new BNews(
                news.authorId,
                news.content,
                news.creationDate(),
                news.description,
                news.id(),
                news.publicationDate,
                news.renderedContent,
                news.source,
                news.status.name(),
                news.title,
                new UrlFriendlyString(news.title).value);
    }
}
