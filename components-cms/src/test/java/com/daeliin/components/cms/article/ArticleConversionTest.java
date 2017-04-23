package com.daeliin.components.cms.article;

import com.daeliin.components.cms.fixtures.ArticleFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.core.sql.BArticle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class ArticleConversionTest {

    private ArticleConversion articleConversion = new ArticleConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        assertThat(articleConversion.map(null, AccountLibrary.admin().id())).isNull();
    }

    @Test
    public void shouldMapArticle() {
        BArticle mappedArticle = articleConversion.map(ArticleLibrary.publishedArticle(),  AccountLibrary.admin().id());

        assertThat(mappedArticle).isEqualToComparingFieldByField(ArticleFixtures.publishedArticle());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(articleConversion.instantiate(null, AccountLibrary.admin().username)).isNull();
    }

    @Test
    public void shouldInstantiateAnArticle() {
        Article rebuiltArticle = articleConversion.instantiate(ArticleFixtures.publishedArticle(), AccountLibrary.admin().username);

        assertThat(rebuiltArticle).isEqualTo(ArticleLibrary.publishedArticle());
    }
}