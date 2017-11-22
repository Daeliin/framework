package com.daeliin.components.cms.news;

import com.daeliin.components.cms.sql.BNews;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public final class NewsConversion {

    public News instantiate(BNews bNews, String author) {
        return new News(
                bNews.getId(),
                bNews.getCreationDate(),
                author,
                bNews.getContent(),
                bNews.getSource(),
                bNews.getPublicationDate(),
                bNews.getPublished());
    }

    public BNews map(News news, String authorId) {
        return new BNews(
                authorId,
                news.content,
                news.getCreationDate(),
                news.getId(),
                news.publicationDate,
                news.published,
                news.source);
    }

    public Set<News> instantiate(Map<BNews, String> authorByNews) {
        return authorByNews
                .keySet()
                .stream()
                .map(news -> instantiate(news, authorByNews.get(news)))
                .collect(toCollection(LinkedHashSet::new));
    }
}
