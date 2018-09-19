package com.daeliin.components.cms.news;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;
import java.util.Objects;

public class News extends PersistentResource<String> implements Comparable<News> {
    
    public final String authorId;
    public final String title;
    public final String urlFriendlyTitle;
    public final String description;
    public final String content;
    public final String renderedContent;
    public final String source;
    public final Instant publicationDate;
    public final NewsStatus status;

    public News(String id, Instant creationDate, String authorId, String title, String urlFriendlyTitle, String description,
                String content, String renderedContent,
                String source, Instant publicationDate, NewsStatus status) {
        super(id, creationDate);
        this.authorId = Objects.requireNonNull(authorId);
        this.title = title;
        this.urlFriendlyTitle = urlFriendlyTitle;
        this.description = description;
        this.content = Objects.requireNonNull(content);
        this.renderedContent = renderedContent;
        this.source = source;
        this.publicationDate = publicationDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("authorId", authorId)
                .add("title", title)
                .add("status", status)
                .add("publicationDate", publicationDate)
                .toString();
    }

    @Override
    public int compareTo(News other) {
        if (this.equals(other)) {
            return 0;
        }

        if (creationDate().equals(other.creationDate())) {
            return 1;
        }

        return this.creationDate().compareTo(other.creationDate());
    }
}
