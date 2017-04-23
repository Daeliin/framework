package com.daeliin.components.cms.fixtures;

import com.daeliin.components.core.sql.BArticle;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class ArticleFixtures {

    public static BArticle publishedArticle() {
        return new BArticle(
                "ACCOUNT1",
                "We open our door today, you'll find content very soon.",
                Timestamp.valueOf(LocalDateTime.of(2016, 5, 20, 14, 30, 0)),
                "Today is the day we start sample.com",
                "ARTICLE1",
                Timestamp.valueOf(LocalDateTime.of(2016, 5, 20, 15, 30, 0)),
                true,
                "Welcome to sample.com",
                "Welcome-to-sample-com");
    }

    public static BArticle notPublishedArticle() {
        return new BArticle(
                "ACCOUNT1",
                "We go live today, here''s our first content.",
                Timestamp.valueOf(LocalDateTime.of(2016, 5, 20, 14, 30, 0)),
                "Today is the day we go live at sample.com",
                "ARTICLE2",
                null,
                false,
                "Sample.com is live",
                "sample-com-is-live");
    }
}
