package com.daeliin.components.cms.news;

import com.daeliin.components.cms.fixtures.NewsFixtures;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.cms.sql.BNews;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class NewsConversionTest {

    private NewsConversion newsConversion = new NewsConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        News nullNews = null;

        newsConversion.map(nullNews);
    }

    @Test
    public void shouldMapNews() {
        BNews mappedNews = newsConversion.map(NewsLibrary.publishedNews());

        assertThat(mappedNews).isEqualToComparingFieldByField(NewsFixtures.publishedNews());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BNews nullNewsRow = null;

        newsConversion.instantiate(nullNewsRow);
    }

    @Test
    public void shouldInstantiateANews() {
        News rebuiltNews = newsConversion.instantiate(NewsFixtures.publishedNews());

        assertThat(rebuiltNews).isEqualTo(NewsLibrary.publishedNews());
    }
}