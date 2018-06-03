package com.daeliin.components.cms.fixtures;

import com.daeliin.components.cms.sql.BNews;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class NewsRows {

    public static BNews publishedNews() {
        return new BNews(
                "ACCOUNT1",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "Today is the day we start sample.com",
                "NEWS1",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                "https://google.fr",
                "PUBLISHED",
                "Welcome to sample",
                "welcome-to-sample");
    }

    public static BNews validatedNews() {
        return new BNews(
                "ACCOUNT2",
                "We go live today, here''s our first content.",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "Today is the day we go live at sample.com",
                "NEWS2",
                null,
                "VALIDATED",
                null,
                "Sample is live",
                "sample-is-live");
    }

    public static BNews draftdNews() {
        return new BNews(
            "ACCOUNT2",
            "We go beta today",
            LocalDateTime.of(2016, 5, 21, 14, 30, 0).toInstant(ZoneOffset.UTC),
            "Today is the day we go beta at sample.com",
            "NEWS3",
            null,
            "VALIDATED",
            null,
            "Sample.com is in beta",
            "sample-is-in-beta");
    }
}
