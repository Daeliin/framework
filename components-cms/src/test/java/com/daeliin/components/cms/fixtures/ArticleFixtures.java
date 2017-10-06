package com.daeliin.components.cms.fixtures;

import com.daeliin.components.cms.sql.BArticle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class ArticleFixtures {

    public static BArticle publishedArticle() {
        return new BArticle(
                "ACCOUNT1",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "Today is the day we start sample.com",
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                true,
                "Welcome to sample",
                "welcome-to-sample");
    }

    public static BArticle notPublishedArticle() {
        return new BArticle(
                "ACCOUNT1",
                "We go live today, here''s our first content.",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "Today is the day we go live at sample.com",
                "ARTICLE2",
                null,
                false,
                "Sample is live",
                "sample-is-live");
    }
}
