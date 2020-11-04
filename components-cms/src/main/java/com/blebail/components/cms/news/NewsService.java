package com.blebail.components.cms.news;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.cms.credentials.account.AccountService;
import com.blebail.components.cms.event.EventLogService;
import com.blebail.components.cms.publication.PublicationStatus;
import com.blebail.components.cms.sql.BNews;
import com.blebail.components.cms.sql.QAccount;
import com.blebail.components.cms.sql.QNews;
import com.blebail.components.core.string.EnglishTitle;
import com.blebail.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
public class NewsService extends ResourceService<News, BNews, String, NewsRepository> {

    private final AccountService accountService;

    private final EventLogService eventLogService;

    @Inject
    public NewsService(NewsRepository repository, AccountService accountService, EventLogService eventLogService) {
        super(repository, new NewsConversion());
        this.accountService = accountService;
        this.eventLogService = eventLogService;
    }

    @Override
    public News create(News news) {
        Account author = accountService.findOne(news.authorId);

        if (!author.enabled) {
            throw new IllegalStateException(String.format("Account %s is not active", author.username));
        }

        News createdNews = super.create(news);

        eventLogService.create("A news has been created");

        return createdNews;
    }

    @Override
    public News update(News news) {
        if (news == null) {
            throw new NoSuchElementException();
        }

        BNews existingNews = repository.findOne(news.id())
            .orElseThrow(() -> new NoSuchElementException(String.format("News %s doesn't exist", news.id())));

        existingNews.setTitle(news.title);
        existingNews.setUrlFriendlyTitle(news.title != null ? new EnglishTitle(news.title).toUrlFriendlyString() : null);
        existingNews.setDescription(news.description);
        existingNews.setContent(news.content);
        existingNews.setRenderedContent(news.renderedContent);
        existingNews.setSource(news.source);

        return conversion.from(repository.save(existingNews));
}

    @Override
    public boolean delete(String id) {
        boolean deleted = repository.delete(id);

        eventLogService.create(String.format("The news %s has been deleted", id));

        return deleted;
    }

    public News markAs(String id, PublicationStatus status) {
        BNews existingNews = repository.findOne(id).orElseThrow(() ->
            new NoSuchElementException(String.format("News %s doesn't exist", id)));

        existingNews.setStatus(status.name());
        existingNews.setPublicationDate(status == PublicationStatus.PUBLISHED ? Instant.now() : null);

        News updatedNews = conversion.from(repository.save(existingNews));

        switch (status) {
            case DRAFT: eventLogService.create(String.format("The news %s has been put in draft", id));
                break;
            case VALIDATED: eventLogService.create(String.format("The news %s has been validated for publication", id));
                break;
            case PUBLISHED: eventLogService.create(String.format("The news %s has been published", id));
                break;
        }

        return updatedNews;
    }

    public Map<News, Account> authorByNews(Collection<String> newsIds) {
        Collection<News> news = find(QNews.news.id.in(newsIds));
        Set<String> accountIds = news.stream().map(newsItem -> newsItem.authorId).collect(toSet());
        Map<String, Account> accountById = accountService.find(QAccount.account.id.in(accountIds))
                .stream()
                .collect(toMap(Account::id, Function.identity()));

        return news.stream()
            .collect(toMap(Function.identity(), newsItem -> accountById.get(newsItem.authorId)));
    }
}
