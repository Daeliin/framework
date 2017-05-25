package com.daeliin.components.cms.news;


import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.ArticleLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class NewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private NewsService newsService;

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenFindingNewsOfUnexistingArticleId() {
        newsService.findForArticle("EZOFZEJF-34324");
    }

    @Test
    public void shouldFindNewsOfAnArticle_orderedByCreationDateAsc() {
        Collection<News> newsOfArticle = newsService.findForArticle(ArticleLibrary.notPublishedArticle().getId());

        assertThat(newsOfArticle).containsExactly(NewsLibrary.newsWithSource(), NewsLibrary.newsWithoutSource());
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenCreatingNewsOfUnexistingArticleId() {
        News news = new News("NEWSID", LocalDateTime.now(), AccountLibrary.admin().username, "Content", null);

        newsService.create("EZOFZEJF-34324", news);
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenCreatingNewsOfUnexistingAuthor() {
        News news = new News("NEWSID", LocalDateTime.now(), "ZADAZD", "Content", null);

        newsService.create(ArticleLibrary.notPublishedArticle().getId(), news);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateException_whenCreatingNewsOnPublishedArticle() {
        News news = new News("NEWSID", LocalDateTime.now(), AccountLibrary.admin().username, "Content", null);

        newsService.create(ArticleLibrary.publishedArticle().getId(), news);
    }

    @Test
    public void shouldCreateANews() {
        News news = new News("", LocalDateTime.now(), AccountLibrary.admin().username, "Content", null);

        News createdNews = newsService.create(ArticleLibrary.notPublishedArticle().getId(), news);

        assertThat(createdNews.getId()).isNotBlank();
        assertThat(createdNews.author).isEqualTo(news.author);
        assertThat(createdNews.content).isEqualTo(news.content);
        assertThat(createdNews.source).isEqualTo(news.source);
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenUpdatingNonExistingNews() {
        newsService.update("OKOK", null);
    }

    @Test
    public void shouldUpdateANewsContentAndSource() {
        News newsToUpdate = NewsLibrary.newsWithSource();
        News news = new News(" ", LocalDateTime.now(), "", "newContent", "http://newsource.com");

        News updatedNews = newsService.update(newsToUpdate.getId(), news);

        assertThat(updatedNews.content).isEqualTo(news.content);
        assertThat(updatedNews.source).isEqualTo(news.source);
        assertThat(updatedNews.getId()).isEqualTo(newsToUpdate.getId());
        assertThat(updatedNews.getCreationDate()).isEqualTo(newsToUpdate.getCreationDate());
        assertThat(updatedNews.author).isEqualTo(newsToUpdate.author);
    }
}