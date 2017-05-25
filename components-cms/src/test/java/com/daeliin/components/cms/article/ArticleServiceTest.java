package com.daeliin.components.cms.article;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.domain.utils.UrlFriendlyString;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class ArticleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private ArticleService articleService;

    @Test
    public void shouldFindArticle() {
        Article article = ArticleLibrary.publishedArticle();
        Article foundArticle = articleService.findOne(article.getId());

        assertThat(foundArticle).isEqualTo(foundArticle);
    }

    @Test
    public void shouldCheckThatArticleDoesntExist() {
        assertThat(articleService.exists("ZFZEF-ZEF")).isFalse();
    }

    @Test
    public void shouldCheckThatArticleExists() {
        assertThat(articleService.exists(ArticleLibrary.publishedArticle().getId())).isTrue();
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenCreatingArticleWithNonExistingAuthor() {
        Article article = new Article(
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0),
                "AANWN",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0),
                true);

        articleService.create(article);
    }

    @Test
    public void shouldCreateArticle() {
        Article article = new Article(
                " ",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0),
                "admin",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0),
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

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenUpdatingNonExistingArticle() {
        articleService.update("OKOK", null);
    }

    @Test
    public void shouldUpdateAnArticleTitleDescriptionAndContent() {
        Article articleToUpdate = ArticleLibrary.notPublishedArticle();
        Article article = new Article("", LocalDateTime.now(), "", "New title", "", "New desc", "New content", null, false);

        Article updatedArtice = articleService.update(articleToUpdate.getId(), article);

        assertThat(updatedArtice.title).isEqualTo(article.title);
        assertThat(updatedArtice.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(article.title).value);
        assertThat(updatedArtice.description).isEqualTo(article.description);
        assertThat(updatedArtice.content).isEqualTo(article.content);
        assertThat(updatedArtice.getId()).isEqualTo(articleToUpdate.getId());
        assertThat(updatedArtice.getCreationDate()).isEqualTo(articleToUpdate.getCreationDate());
        assertThat(updatedArtice.author).isEqualTo(articleToUpdate.author);
    }
}
