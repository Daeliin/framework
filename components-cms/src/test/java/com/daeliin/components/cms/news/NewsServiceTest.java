package com.daeliin.components.cms.news;


import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.article.Article;
import com.daeliin.components.cms.fixtures.ArticleFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsService newsService;

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingNewsOfUnexistingArticleId() {
        newsService.findForArticle("EZOFZEJF-34324");
    }

    @Test
    public void shouldFindNewsOfAnArticle_orderedByCreationDateAsc() {
        Collection<News> newsOfArticle = newsService.findForArticle(ArticleLibrary.notPublishedArticle().getId());

        assertThat(newsOfArticle).containsExactly(NewsLibrary.newsWithSource(), NewsLibrary.newsWithoutSource());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingNewsOfUnexistingArticleId() {
        News news = new News("NEWSID", Instant.now(), AccountLibrary.admin().username, "Content", null);

        newsService.create("EZOFZEJF-34324", news);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingNewsOfUnexistingAuthor() {
        News news = new News("NEWSID", Instant.now(), "ZADAZD", "Content", null);

        newsService.create(ArticleLibrary.notPublishedArticle().getId(), news);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenCreatingNewsOnPublishedArticle() {
        News news = new News("NEWSID", Instant.now(), AccountLibrary.admin().username, "Content", null);

        newsService.create(ArticleLibrary.publishedArticle().getId(), news);
    }

    @Test
    public void shouldCreateANews() {
        News news = new News("", Instant.now(), AccountLibrary.admin().username, "Content", null);

        News createdNews = newsService.create(ArticleLibrary.notPublishedArticle().getId(), news);

        assertThat(createdNews.getId()).isNotBlank();
        assertThat(createdNews.author).isEqualTo(news.author);
        assertThat(createdNews.content).isEqualTo(news.content);
        assertThat(createdNews.source).isEqualTo(news.source);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUpdatingNonExistingNews() {
        newsService.update("OKOK", null);
    }

    @Test
    public void shouldUpdateANewsContentAndSource() {
        News newsToUpdate = NewsLibrary.newsWithSource();
        News news = new News(" ", Instant.now(), "", "newContent", "http://newsource.com");

        News updatedNews = newsService.update(newsToUpdate.getId(), news);

        assertThat(updatedNews.content).isEqualTo(news.content);
        assertThat(updatedNews.source).isEqualTo(news.source);
        assertThat(updatedNews.getId()).isEqualTo(newsToUpdate.getId());
        assertThat(updatedNews.getCreationDate()).isEqualTo(newsToUpdate.getCreationDate());
        assertThat(updatedNews.author).isEqualTo(newsToUpdate.author);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingNonExistingNews() {
        newsService.findOne("nonExistingId");
    }

    @Test
    public void shouldFindNews() {
        News existingNews = newsService.findOne(NewsLibrary.newsWithSource().getId());

        assertThat(existingNews).isEqualTo(NewsLibrary.newsWithSource());
    }

    @Test
    public void shouldCheckThatANewsDoesntExist() {
        assertThat(newsService.exists("nonExistingId")).isFalse();
    }

    @Test
    public void shouldCheckThatANewsExists() {
        assertThat(newsService.exists(NewsLibrary.newsWithSource().getId())).isTrue();
    }

    @Test
    public void shouldDeleteANews() {
        newsService.delete(NewsLibrary.newsWithSource().getId());

        assertThat(newsService.exists(NewsLibrary.newsWithSource().getId())).isFalse();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenDeletingNonExistingNews() {
        newsService.delete("nonExisingId");
    }

    @Test
    public void shouldCountNewsByArticle() {
        final Article article = ArticleLibrary.notPublishedArticle();

        Map<Article, Long> newsCountByArticle = newsService.countByArticle(Sets.newHashSet(article));

        assertThat(newsCountByArticle.get(article)).isEqualTo(2);
    }

    @Test
    public void shouldCountZeroNews_whenArticleHasNoNews() {
        final Article article = ArticleLibrary.publishedArticle();

        Map<Article, Long> newsCountByArticleId = newsService.countByArticle(Sets.newHashSet(article));

        assertThat(newsCountByArticleId.get(article)).isEqualTo(0);
    }
}