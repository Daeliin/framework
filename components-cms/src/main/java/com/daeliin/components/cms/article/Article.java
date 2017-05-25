package com.daeliin.components.cms.article;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article extends PersistentResource<String> implements Comparable<Article> {
    
    public final String author;
    public final String title;
    public final String urlFriendlyTitle;
    public final String description;
    public final String content;
    public final LocalDateTime publicationDate;
    public final boolean published;

    public Article(String id, LocalDateTime creationDate, String author, String title, String urlFriendlyTitle, String description, String content, LocalDateTime publicationDate, boolean published) {
        super(id, creationDate);
        this.author = Objects.requireNonNull(author, "author should not be null");
        this.title = Objects.requireNonNull(title, "title should not be null");
        this.urlFriendlyTitle = Objects.requireNonNull(urlFriendlyTitle, "urlFriendlyTitle should not be null");
        this.description = Objects.requireNonNull(description, "description should not be null");
        this.content = content;
        this.publicationDate = publicationDate;
        this.published = published;
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("author", author)
                .add("title", title)
                .add("published", published)
                .add("publicationDate", publicationDate)
                .toString();
    }

    @Override
    public int compareTo(Article other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.getCreationDate().compareTo(other.getCreationDate());
    }
}
