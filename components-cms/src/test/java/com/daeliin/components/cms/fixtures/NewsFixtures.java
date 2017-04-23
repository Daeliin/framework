package com.daeliin.components.cms.fixtures;

import com.daeliin.components.core.sql.BNews;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class NewsFixtures {

    public static BNews newsWithSource() {
        return new BNews(
                "ARTICLE2",
                "ACCOUNT1",
                "Some news content 1",
                Timestamp.valueOf(LocalDateTime.of(2016, 5, 20, 14, 30, 0)),
                "NEWS1", "https://daeliin.com");
    }
    
    public static BNews newsWithoutSource() {
        return new BNews(
                "ARTICLE2",
                "ACCOUNT1",
                "Some news content 2",
                Timestamp.valueOf(LocalDateTime.of(2016, 5, 21, 14, 30, 0)),
                "NEWS2",
                null);
    }
}
