package com.daeliin.components.cms.news;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.fixtures.ArticleFixtures;
import com.daeliin.components.cms.sql.BNews;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsRepository newsRepository;

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(NewsRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }

    @Test
    public void shouldDeleteAllNewsOfAnArticle() {
        newsRepository.deleteForArticle(ArticleFixtures.notPublishedArticle().getId());

        assertThat(newsRepository.findAll()).isEmpty();
    }

    @Test
    public void shouldFindNewsByArticleId() {
        final String articleId = ArticleFixtures.notPublishedArticle().getId();

        Map<String, Set<BNews>> newsByArticleId = newsRepository.findByArticleId(Sets.newHashSet(articleId));

        assertThat(newsByArticleId.get(articleId)).hasSize(2);
    }

    @Test
    public void shouldFindZeroNews_whenArticleHasNoNews() {
        final String articleId = ArticleFixtures.publishedArticle().getId();

        Map<String, Set<BNews>> newsByArticleId = newsRepository.findByArticleId(Sets.newHashSet(articleId));

        assertThat(newsByArticleId.get(articleId)).isEmpty();
    }
}