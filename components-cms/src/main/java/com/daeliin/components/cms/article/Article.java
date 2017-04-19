package com.daeliin.components.cms.article;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article extends PersistentResource implements Comparable<Article> {
    
    private static final long serialVersionUID = -7808122481259070912L;

    public final String author;
    public final String title;
    public final String urlFriendlyTitle;
    public final String description;
    public final String content;
    public final LocalDateTime publicationDate;
    public final boolean published;

    public Article(String uuid, LocalDateTime creationDate, String author, String title, String urlFriendlyTitle, String description, String content, LocalDateTime publicationDate, boolean published) {
        super(uuid, creationDate);
        this.author = Objects.requireNonNull(author, "author should not be null");
        this.title = Objects.requireNonNull(title, "title should not be null");;
        this.urlFriendlyTitle = urlFriendlyTitle;
        this.description = description;
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
                .toString();
    }

    @Override
    public int compareTo(Article other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.creationDate.compareTo(other.creationDate);
    }
}
