package com.daeliin.components.cms.news;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.fixtures.ArticleFixtures;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.HashSet;
import java.util.Map;

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
    public void shouldCountNewsByArticleId() {
        final String articleId = ArticleFixtures.notPublishedArticle().getId();

        Map<String, Long> newsCountByArticleId = newsRepository.countByArticleId(Sets.newHashSet(articleId));

        assertThat(newsCountByArticleId.get(articleId)).isEqualTo(2);
    }

    @Test
    public void shouldCountZeroNews_whenArticleHasNoNews() {
        final String articleId = ArticleFixtures.publishedArticle().getId();

        Map<String, Long> newsCountByArticleId = newsRepository.countByArticleId(Sets.newHashSet(articleId));

        assertThat(newsCountByArticleId.get(articleId)).isEqualTo(0);
    }
}