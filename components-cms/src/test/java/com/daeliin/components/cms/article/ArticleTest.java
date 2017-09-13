package com.daeliin.components.cms.article;

import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.domain.resource.PersistentResource;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public final class ArticleTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(Article.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test
    public void shouldPrintItsAuthorTitlePublishedAndPublicationDate() {
        Article article = ArticleLibrary.publishedArticle();

        assertThat(article.toString()).contains(
                article.author,
                article.title,
                String.valueOf(article.published),
                article.publicationDate.toString());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenAuthorIsNull() {
        new Article("ARTICLEID1", Instant.now(), null, "Hello world", "hello-world", "Desc", "Content", null, false);
    }

    @Test
    public void shouldAssignAnAuthor() {
        Article article = new Article("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", null, false);

        assertThat(article.author).isEqualTo("john");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenTitleIsNull() {
        new Article("ARTICLEID1", Instant.now(), "john", null, "hello-world", "Desc", "Content", null, false);
    }

    @Test
    public void shouldAssignATitle() {
        Article article = new Article("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", null, false);

        assertThat(article.title).isEqualTo("Hello world");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenUrlFriendlyTitleIsNull() {
        new Article("ARTICLEID1", Instant.now(), "john", "Hello world", null, "Desc", "Content", null, false);
    }

    @Test
    public void shouldAssignAnUrlFriendlyTitle() {
        Article article = new Article("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", null, false);

        assertThat(article.urlFriendlyTitle).isEqualTo("hello-world");
    }

    @Test
    public void shouldAssignADescription() {
        Article article = new Article("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", null, false);

        assertThat(article.description).isEqualTo("Desc");
    }

    @Test
    public void shouldBeComparedOnCreationDate() {
        Article article1 = new Article("ARTICLEID1", Instant.now(), "john", "Hello world", "hello-world", "Desc", "Content", null, false);
        Article article2 = new Article("ARTICLEID2", Instant.now().plus(10, ChronoUnit.SECONDS), "john", "Hello world", "hello-world", "Desc", "Content", null, false);

        assertThat(article1.compareTo(article2)).isNegative();
        assertThat(article2.compareTo(article1)).isPositive();
    }
}