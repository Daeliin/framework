package com.daeliin.components.cms.news;

import com.daeliin.components.cms.fixtures.NewsFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.cms.sql.BNews;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class NewsConversionTest {

    private NewsConversion newsConversion = new NewsConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        newsConversion.map(null, ArticleLibrary.notPublishedArticle().getId(), AccountLibrary.admin().getId());
    }

    @Test
    public void shouldMapNews() {
        BNews mappedNews = newsConversion.map(NewsLibrary.newsWithSource(), ArticleLibrary.notPublishedArticle().getId(), AccountLibrary.admin().getId());

        assertThat(mappedNews).isEqualToComparingFieldByField(NewsFixtures.newsWithSource());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        newsConversion.instantiate(null, AccountLibrary.admin().username);
    }

    @Test
    public void shouldInstantiateAnNews() {
        News rebuiltNews = newsConversion.instantiate(NewsFixtures.newsWithSource(), AccountLibrary.admin().username);

        assertThat(rebuiltNews).isEqualTo(NewsLibrary.newsWithSource());
    }
}