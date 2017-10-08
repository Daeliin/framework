package com.daeliin.components.cms.article;

import com.daeliin.components.cms.fixtures.ArticleFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.sql.BArticle;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class ArticleConversionTest {

    private ArticleConversion articleConversion = new ArticleConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        articleConversion.map(null, AccountLibrary.admin().getId());
    }

    @Test
    public void shouldMapArticle() {
        BArticle mappedArticle = articleConversion.map(ArticleLibrary.publishedArticle(),  AccountLibrary.admin().getId());

        assertThat(mappedArticle).isEqualToComparingFieldByField(ArticleFixtures.publishedArticle());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        articleConversion.instantiate(null, AccountLibrary.admin().username);
    }

    @Test
    public void shouldInstantiateAnArticle() {
        Article rebuiltArticle = articleConversion.instantiate(ArticleFixtures.publishedArticle(), AccountLibrary.admin().username);

        assertThat(rebuiltArticle).isEqualTo(ArticleLibrary.publishedArticle());
    }
}