package com.daeliin.components.cms.news;

import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.persistence.resource.PersistentResource;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public final class NewsTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(News.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenAuthorIsNull() {
        new News("NEWSID", Instant.now(), null, "This is a news", "http://daeliin.com", null, false);
    }

    @Test
    public void shouldAssignAnAuthor() {
        News news = new News("NEWSID", Instant.now(), "john", "This is a news", "http://daeliin.com", null, false);

        assertThat(news.author).isEqualTo("john");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenContentIsNull() {
        new News("NEWSID", Instant.now(), "john", null, "http://daeliin.com", null, false);
    }

    @Test
    public void shouldAssignAContent() {
        News news = new News("NEWSID", Instant.now(), "john", "This is a news", "http://daeliin.com", null, false);

        assertThat(news.content).isEqualTo("This is a news");
    }

    @Test
    public void shouldAssignASource() {
        News news = new News("NEWSID", Instant.now(), "john", "This is a news", "http://daeliin.com", null, false);

        assertThat(news.source).isEqualTo("http://daeliin.com");
    }

    @Test
    public void shouldAssignAPublicationDate() {
        Instant publicationDate = Instant.now();
        News news = new News("NEWSID", Instant.now(), "john", "This is a news", "http://daeliin.com", publicationDate, true);

        assertThat(news.publicationDate).isEqualTo(publicationDate);
    }

    @Test
    public void shouldAssignPublished() {
        News news = new News("NEWSID", Instant.now(), "john", "This is a news", "http://daeliin.com", null, false);

        assertThat(news.published).isFalse();
    }

    @Test
    public void shouldPrintItsAuthorContentSourceAndPublicationDate() {
        News news = NewsLibrary.newsWithSource();

        assertThat(news.toString()).contains(news.author, news.content, news.source, news.publicationDate.toString());
    }

    @Test
    public void shouldBeComparedOnCreationDate() {
        News news1 = new News("NEWSID1", Instant.now(), "john", "This is a news", "http://daeliin.com", null, false);
        News news2 = new News("NEWSID2", Instant.now().plus(10, ChronoUnit.SECONDS), "john", "This is a news", "http://daeliin.com", null, false);

        assertThat(news1.compareTo(news2)).isNegative();
        assertThat(news2.compareTo(news1)).isPositive();
    }
}
