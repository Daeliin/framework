package com.daeliin.components.cms.library;

import com.daeliin.components.cms.news.News;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class NewsLibrary {

    public static News newsWithSource() {
        return new News(
                "NEWS1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "admin",
                "Some news content 1",
                "https://daeliin.com");
    }

    public static News newsWithoutSource() {
        return new News(
                "NEWS2",
                LocalDateTime.of(2016, 5, 21, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "admin",
                "Some news content 2",
                null);
    }
}
