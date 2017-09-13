package com.daeliin.components.cms.fixtures;

import com.daeliin.components.core.sql.BNews;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class NewsFixtures {

    public static BNews newsWithSource() {
        return new BNews(
                "ARTICLE2",
                "ACCOUNT1",
                "Some news content 1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "NEWS1", "https://daeliin.com");
    }
    
    public static BNews newsWithoutSource() {
        return new BNews(
                "ARTICLE2",
                "ACCOUNT1",
                "Some news content 2",
                LocalDateTime.of(2016, 5, 21, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "NEWS2",
                null);
    }
}
