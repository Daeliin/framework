package com.daeliin.components.cms.news;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;
import java.util.Objects;

public class News extends PersistentResource<String> implements Comparable<News> {

    public final String author;
    public final String content;
    public final String source;
    public final Instant publicationDate;
    public final boolean published;

    public News(String id, Instant creationDate, String author, String content, String source, Instant publicationDate, boolean published) {
        super(id, creationDate);
        this.author = Objects.requireNonNull(author, "author should not be null");
        this.content = Objects.requireNonNull(content, "content should not be null");
        this.source = source;
        this.publicationDate = publicationDate;
        this.published = published;
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("author", author)
                .add("content", content)
                .add("source", source)
                .add("publicationDate", publicationDate)
                .add("published", published)
                .toString();
    }

    @Override
    public int compareTo(News other) {
        if (this.equals(other)) {
            return 0;
        }
        
        return this.getCreationDate().compareTo(other.getCreationDate());
    }
}
