package com.daeliin.components.cms.news;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.core.string.UrlFriendlyString;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NewsServiceIT {

    @Inject
    private NewsService newsService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory,
        sequenceOf(
            JavaFixtures.account(),
            JavaFixtures.news()
        )
    );

    @Test
    public void shouldFindNews() {
        dbFixture.noRollback();

        News news = NewsLibrary.publishedNews();
        News foundNews = newsService.findOne(news.id());

        assertThat(foundNews).isEqualTo(foundNews);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenfindingANewsThatDoesntExist() {
        dbFixture.noRollback();

        newsService.findOne("nonExistingId");
    }

    @Test
    public void shouldCheckThatNewsDoesntExist() {
        dbFixture.noRollback();

        assertThat(newsService.exists("ZFZEF-ZEF")).isFalse();
    }

    @Test
    public void shouldCheckThatNewsExists() {
        dbFixture.noRollback();

        assertThat(newsService.exists(NewsLibrary.publishedNews().id())).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenCreatingNewsWithNonExistingAuthor() {
        dbFixture.noRollback();

        News news = new News(
                "ARTICLE1",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                "AANWN",
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                "https://google.fr",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                NewsStatus.DRAFT);

        newsService.create(news);
    }

    @Test
    public void shouldCreateNews() {
        News news = new News(
                " ",
                LocalDateTime.of(2016, 5, 20, 14, 30, 0).toInstant(ZoneOffset.UTC),
                AccountLibrary.admin().id(),
                "Welcome to sample",
                "welcome-to-sample",
                "Today is the day we start sample.com",
                "Today is the day we start sample.com",
                "We open our door today, you'll find content very soon.",
                "https://google.fr",
                LocalDateTime.of(2016, 5, 20, 15, 30, 0).toInstant(ZoneOffset.UTC),
                NewsStatus.DRAFT);

        News createdNews = newsService.create(news);

        assertThat(createdNews.id()).isNotBlank();
        assertThat(createdNews.title).isEqualTo(news.title);
        assertThat(createdNews.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(news.title).value);
        assertThat(createdNews.description).isEqualTo(news.description);
        assertThat(createdNews.content).isEqualTo(news.content);
        assertThat(createdNews.source).isEqualTo(news.source);
        assertThat(createdNews.authorId).isEqualTo(news.authorId);
        assertThat(createdNews.publicationDate).isNull();
        assertThat(createdNews.status).isEqualTo(NewsStatus.DRAFT);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenUpdatingNonExistingNews() {
        dbFixture.noRollback();

        News nullNews = null;
        newsService.update(nullNews);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenUpdatingValidatedNews() {
        dbFixture.noRollback();

        newsService.update(NewsLibrary.validatedNews());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenUpdatingPublishedNews() {
        dbFixture.noRollback();

        newsService.update(NewsLibrary.publishedNews());
    }

    @Test
    public void shouldUpdateANewsTitleDescriptionContentAndSource() {
        News newsToUpdate = NewsLibrary.draftNews();
        News news = new News(newsToUpdate.id(), Instant.now(), "", "New title", "", "New desc", "New content", "New content",
                "https://google.fr", null, NewsStatus.PUBLISHED);

        News updatedArtice = newsService.update(news);

        assertThat(updatedArtice.status).isEqualTo(NewsStatus.DRAFT);
        assertThat(updatedArtice.title).isEqualTo(news.title);
        assertThat(updatedArtice.urlFriendlyTitle).isEqualTo(new UrlFriendlyString(news.title).value);
        assertThat(updatedArtice.description).isEqualTo(news.description);
        assertThat(updatedArtice.content).isEqualTo(news.content);
        assertThat(updatedArtice.source).isEqualTo(news.source);
        assertThat(updatedArtice.id()).isEqualTo(newsToUpdate.id());
        assertThat(updatedArtice.creationDate()).isEqualTo(newsToUpdate.creationDate());
        assertThat(updatedArtice.authorId).isEqualTo(newsToUpdate.authorId);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenMarkingANonExistingNewsAsDraft() {
        dbFixture.noRollback();

        newsService.markAs("nonExistingId", NewsStatus.DRAFT);
    }

    @Test
    public void shouldMarkANewsAsDraft() {
        News news = newsService.markAs(NewsLibrary.validatedNews().id(), NewsStatus.DRAFT);

        assertThat(news.status).isEqualTo(NewsStatus.DRAFT);
        assertThat(news.publicationDate).isNull();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenMArkingANonExistingNewsAsValidated() {
        dbFixture.noRollback();

        newsService.markAs("nonExistingId", NewsStatus.VALIDATED);
    }

    @Test
    public void shouldMarkANewsAsValidated() {
        News news = newsService.markAs(NewsLibrary.draftNews().id(), NewsStatus.VALIDATED);

        assertThat(news.status).isEqualTo(NewsStatus.VALIDATED);
        assertThat(news.publicationDate).isNull();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenMarkingANonExistingNewsAsPublished() {
        dbFixture.noRollback();

        newsService.markAs("nonExistingId", NewsStatus.PUBLISHED);
    }

    @Test
    public void shouldMarkANewsAsPublished() {
        News news = newsService.markAs(NewsLibrary.validatedNews().id(), NewsStatus.PUBLISHED);

        assertThat(news.status).isEqualTo(NewsStatus.PUBLISHED);
        assertThat(news.publicationDate).isNotNull();
    }

    @Test
    public void shouldFindAuthorByNews() {
        dbFixture.noRollback();

        List<String> news = Arrays.asList(NewsLibrary.publishedNews().id(), NewsLibrary.validatedNews().id());

        Map<News, Account> authorByNews = newsService.authorByNews(news);

        assertThat(authorByNews.get(NewsLibrary.publishedNews())).isEqualTo(AccountLibrary.admin());
        assertThat(authorByNews.get(NewsLibrary.validatedNews())).isEqualTo(AccountLibrary.john());
    }
}
