package com.daeliin.components.cms.news;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

public class News extends PersistentResource implements Comparable<News> {

    public final String author;
    public final String content;
    public final String source;

    public News(String id, LocalDateTime creationDate, String author, String content, String source) {
        super(id, creationDate);
        this.author = Objects.requireNonNull(author, "author should not be null");
        this.content = Objects.requireNonNull(content, "content should not be null");
        this.source = source;
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("author", author)
                .add("content", content)
                .add("source", source)
                .toString();
    }

    @Override
    public int compareTo(News other) {
        if (this.equals(other)) {
            return 0;
        }
        
        return this.creationDate.compareTo(other.creationDate);
    }
}
