package com.daeliin.components.cms.news;


import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.Instant;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsService newsService;

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingNewsOfUnexistingAuthor() {
        News news = new News("NEWSID", Instant.now(), "ZADAZD", "Content", null, null, false);

        newsService.create(news);
    }

    @Test
    public void shouldCreateANews() {
        News news = new News("", Instant.now(), AccountLibrary.admin().username, "Content", null, null, false);

        News createdNews = newsService.create(news);

        assertThat(createdNews.getId()).isNotBlank();
        assertThat(createdNews.author).isEqualTo(news.author);
        assertThat(createdNews.content).isEqualTo(news.content);
        assertThat(createdNews.source).isEqualTo(news.source);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUpdatingNonExistingNews() {
        newsService.update("OKOK", null);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenUpdatingPublishedNews() {
        newsService.update(NewsLibrary.newsWithSource().getId(), null);
    }

    @Test
    public void shouldUpdateANewsContentAndSource() {
        News newsToUpdate = NewsLibrary.newsWithoutSource();
        News news = new News(" ", Instant.now(), "", "newContent", "http://newsource.com", null, false);

        News updatedNews = newsService.update(newsToUpdate.getId(), news);

        assertThat(updatedNews.content).isEqualTo(news.content);
        assertThat(updatedNews.source).isEqualTo(news.source);
        assertThat(updatedNews.getId()).isEqualTo(newsToUpdate.getId());
        assertThat(updatedNews.getCreationDate()).isEqualTo(newsToUpdate.getCreationDate());
        assertThat(updatedNews.author).isEqualTo(newsToUpdate.author);
        assertThat(updatedNews.publicationDate).isNull();
        assertThat(updatedNews.published).isFalse();
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
    public void shouldPublishANews() {
        News publishedNews = newsService.publish(NewsLibrary.newsWithoutSource().getId());

        assertThat(publishedNews.published).isTrue();
        assertThat(publishedNews.publicationDate).isNotNull();
    }

    @Test
    public void shouldUnpublishANews() {
        News unpublishedNews = newsService.unpublish(NewsLibrary.newsWithSource().getId());

        assertThat(unpublishedNews.published).isFalse();
        assertThat(unpublishedNews.publicationDate).isNull();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenPublishingANonExistingNews() {
        newsService.publish("nonExistingId");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUnpublishingANonExistingNews() {
        newsService.unpublish("nonExistingId");
    }
}