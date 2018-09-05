package com.daeliin.components.cms.news;

import com.daeliin.components.cms.library.NewsLibrary;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public final class NewsTest {

    @Test
    public void shouldPrintItsAuthorTitlePublishedAndPublicationDate() {
        News news = NewsLibrary.publishedNews();

        assertThat(news.toString()).contains(
                news.authorId,
                news.title,
                String.valueOf(news.status),
                news.publicationDate.toString());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenAuthorIsNull() {
        new News("ARTICLEID1", Instant.now(), null, "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);
    }

    @Test
    public void shouldAssignAnAuthor() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news.authorId).isEqualTo("john");
    }

    @Test
    public void shouldAssignATitle() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news.title).isEqualTo("Hello world");
    }

    @Test
    public void shouldAssignAnUrlFriendlyTitle() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news.urlFriendlyTitle).isEqualTo("hello-world");
    }

    @Test
    public void shouldAssignADescription() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news.description).isEqualTo("Desc");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenContentIsNull() {
        new News("ARTICLEID1", Instant.now(), "author", "Hello world", "hello-world", "Desc", null, null, null, null, NewsStatus.DRAFT);
    }

    @Test
    public void shouldAssignAContent() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news.content).isEqualTo("Content");
    }

    @Test
    public void shouldAssignASource() {
        News news = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", "https://google.com", null, NewsStatus.DRAFT);

        assertThat(news.source).isEqualTo("https://google.com");
    }

    @Test
    public void shouldBeComparedOnCreationDate() {
        News news1 = new News("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);
        News news2 = new News("ARTICLEID2", Instant.now().plus(10, ChronoUnit.SECONDS), "john", "Hello world", "hello-world", "Desc", "Content", "Content", null, null, NewsStatus.DRAFT);

        assertThat(news1.compareTo(news2)).isNegative();
        assertThat(news2.compareTo(news1)).isPositive();
    }
}