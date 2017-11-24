package com.daeliin.components.cms.news;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsService newsService;

    @Test
    public void shouldExtendResourceService() {
        assertThat(NewsService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldFindNews() {
        News news = NewsLibrary.publishedNews();
        News foundNews = newsService.findOne(news.getId());

        assertThat(foundNews).isEqualTo(foundNews);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenfindingANewsThatDoesntExist() {
        newsService.findOne("nonExistingId");
    }

    @Test
    public void shouldCheckThatNewsDoesntExist() {
        assertThat(newsService.exists("ZFZEF-ZEF")).isFalse();
    }

    @Test
    public void shouldCheckThatNewsExists() {
        assertThat(newsService.exists(NewsLibrary.publishedNews().getId())).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingNewsWithNonExistingAuthor() {
        News news = new News(
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "AANWN",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                "https://google.fr",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                true);

        newsService.create(news);
    }

    @Test
    public void shouldCreateNews() {
        News news = new News(
                " ",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                AccountLibrary.admin().getId(),
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                "https://google.fr",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                true);

        News createdNews = newsService.create(news);

        assertThat(createdNews.getId()).isNotBlank();
        assertThat(createdNews.title).isEqualTo(news.title);
        assertThat(createdNews.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(news.title).value);
        assertThat(createdNews.description).isEqualTo(news.description);
        assertThat(createdNews.content).isEqualTo(news.content);
        assertThat(createdNews.source).isEqualTo(news.source);
        assertThat(createdNews.authorId).isEqualTo(news.authorId);
        assertThat(createdNews.publicationDate).isNull();
        assertThat(createdNews.published).isFalse();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUpdatingNonExistingNews() {
        News nullNews = null;
        newsService.update(nullNews);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenUpdatingPublishedNews() {
        newsService.update(NewsLibrary.publishedNews());
    }

    @Test
    public void shouldUpdateANewsTitleDescriptionContentAndSource() {
        News newsToUpdate = NewsLibrary.notPublishedNews();
        News news = new News(newsToUpdate.getId(), Instant.now(), "", "New title", "", "New desc", "New content", "https://google.fr", null, false);

        News updatedArtice = newsService.update(news);

        assertThat(updatedArtice.title).isEqualTo(news.title);
        assertThat(updatedArtice.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(news.title).value);
        assertThat(updatedArtice.description).isEqualTo(news.description);
        assertThat(updatedArtice.content).isEqualTo(news.content);
        assertThat(updatedArtice.source).isEqualTo(news.source);
        assertThat(updatedArtice.getId()).isEqualTo(newsToUpdate.getId());
        assertThat(updatedArtice.getCreationDate()).isEqualTo(newsToUpdate.getCreationDate());
        assertThat(updatedArtice.authorId).isEqualTo(newsToUpdate.authorId);
    }

    @Test
    public void shouldPublishANews() {
        News publishedNews = newsService.publish(NewsLibrary.notPublishedNews().getId());

        assertThat(publishedNews.published).isTrue();
        assertThat(publishedNews.publicationDate).isNotNull();
    }

    @Test
    public void shouldUnpublishANews() {
        News unpublishedNews = newsService.unpublish(NewsLibrary.publishedNews().getId());

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
