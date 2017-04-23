package com.daeliin.components.cms.library;

import com.daeliin.components.cms.article.Article;

import java.time.LocalDateTime;

public final class ArticleLibrary {

    public static Article publishedArticle() {
        return new Article(
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0),
                "admin",
                "Welcome to sample.com",
                "Welcome-to-sample-com",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0),
                true);
    }

    public static Article notPublishedArticle() {
        return new Article(
                "ARTICLE2",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0),
                "admin",
                "Sample.com is live",
                "sample-com-is-live"
                "Today is the day we go live at sample.com",s
                "We go live today, here''s our first content.",
                null,
                false);
    }
}
