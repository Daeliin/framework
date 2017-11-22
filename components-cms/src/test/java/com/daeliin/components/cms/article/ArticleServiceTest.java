package com.daeliin.components.cms.article;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.news.NewsRepository;
import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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
public class ArticleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private ArticleService articleService;

    @Inject
    private NewsRepository newsRepository;

    @Test
    public void shouldFindArticle() {
        Article article = ArticleLibrary.publishedArticle();
        Article foundArticle = articleService.findOne(article.getId());

        assertThat(foundArticle).isEqualTo(foundArticle);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenfindingAnArticleThatDoesntExist() {
        articleService.findOne("nonExistingId");
    }

    @Test
    public void shouldCheckThatArticleDoesntExist() {
        assertThat(articleService.exists("ZFZEF-ZEF")).isFalse();
    }

    @Test
    public void shouldCheckThatArticleExists() {
        assertThat(articleService.exists(ArticleLibrary.publishedArticle().getId())).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingArticleWithNonExistingAuthor() {
        Article article = new Article(
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "AANWN",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                true);

        articleService.create(article);
    }

    @Test
    public void shouldCreateArticle() {
        Article article = new Article(
                " ",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "admin",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                true);

        Article createdArticle = articleService.create(article);

        assertThat(createdArticle.getId()).isNotBlank();
        assertThat(createdArticle.title).isEqualTo(article.title);
        assertThat(createdArticle.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(article.title).value);
        assertThat(createdArticle.description).isEqualTo(article.description);
        assertThat(createdArticle.content).isEqualTo(article.content);
        assertThat(createdArticle.author).isEqualTo(article.author);
        assertThat(createdArticle.publicationDate).isNull();
        assertThat(createdArticle.published).isFalse();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUpdatingNonExistingArticle() {
        articleService.update("OKOK", null);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenUpdatingPublishedArticle() {
        articleService.update(ArticleLibrary.publishedArticle().getId(), null);
    }

    @Test
    public void shouldUpdateAnArticleTitleDescriptionAndContent() {
        Article articleToUpdate = ArticleLibrary.notPublishedArticle();
        Article article = new Article("", Instant.now(), "", "New title", "", "New desc", "New content", null, false);

        Article updatedArtice = articleService.update(articleToUpdate.getId(), article);

        assertThat(updatedArtice.title).isEqualTo(article.title);
        assertThat(updatedArtice.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(article.title).value);
        assertThat(updatedArtice.description).isEqualTo(article.description);
        assertThat(updatedArtice.content).isEqualTo(article.content);
        assertThat(updatedArtice.getId()).isEqualTo(articleToUpdate.getId());
        assertThat(updatedArtice.getCreationDate()).isEqualTo(articleToUpdate.getCreationDate());
        assertThat(updatedArtice.author).isEqualTo(articleToUpdate.author);
    }

    @Test
    public void shouldFindAllArticles() {
        PageRequest pageRequest = new PageRequest(0, 5, Sets.newLinkedHashSet(ImmutableSet.of(new Sort("creationDate", Sort.Direction.DESC))));

        Page<Article> articlePage = articleService.findAll(pageRequest);

        assertThat(articlePage.items).containsExactly(ArticleLibrary.notPublishedArticle(), ArticleLibrary.publishedArticle());
    }

    @Test
    public void shouldPublishAnArticle() {
        Article publishedArticle = articleService.publish(ArticleLibrary.notPublishedArticle().getId());

        assertThat(publishedArticle.published).isTrue();
        assertThat(publishedArticle.publicationDate).isNotNull();
    }

    @Test
    public void shouldUnpublishAnArticle() {
        Article unpublishedArticle = articleService.unpublish(ArticleLibrary.publishedArticle().getId());

        assertThat(unpublishedArticle.published).isFalse();
        assertThat(unpublishedArticle.publicationDate).isNull();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenPublishingANonExistingArticle() {
        articleService.publish("nonExistingId");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUnpublishingANonExistingArticle() {
        articleService.unpublish("nonExistingId");
    }

    @Test
    public void shouldDeleteAnArticle() {
        boolean deleted = articleService.delete(ArticleLibrary.publishedArticle().getId());

        assertThat(deleted).isTrue();
    }
}
