package com.daeliin.components.cms.news;

import com.daeliin.components.cms.fixtures.NewsFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.core.sql.BNews;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class NewsConversionTest {

    private NewsConversion newsConversion = new NewsConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        assertThat(newsConversion.map(null, ArticleLibrary.notPublishedArticle().getId(), AccountLibrary.admin().getId())).isNull();
    }

    @Test
    public void shouldMapNews() {
        BNews mappedNews = newsConversion.map(NewsLibrary.newsWithSource(), ArticleLibrary.notPublishedArticle().getId(), AccountLibrary.admin().getId());

        assertThat(mappedNews).isEqualToComparingFieldByField(NewsFixtures.newsWithSource());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(newsConversion.instantiate(null, AccountLibrary.admin().username)).isNull();
    }

    @Test
    public void shouldInstantiateAnNews() {
        News rebuiltNews = newsConversion.instantiate(NewsFixtures.newsWithSource(), AccountLibrary.admin().username);

        assertThat(rebuiltNews).isEqualTo(NewsLibrary.newsWithSource());
    }
}