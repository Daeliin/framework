package com.daeliin.components.cms.article;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"title", "author", "creationDate", "publicationDate"}, callSuper = true)
@Entity
public class Article extends UUIDPersistentResource implements Comparable<Article> {
    
    private static final long serialVersionUID = -7808122481259070912L;

    @NotNull
    @OneToOne
    private Account author;
    
    @NotBlank
    private String title;
    
    @NotBlank
    @Type(type = "text")
    private String description;
    
    @NotBlank
    @Type(type = "text")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date")
    private Date publicationDate;
    
    @NotNull
    private boolean published = false;

    public Article() {
        this.creationDate = new Date();
    }
    
    public Article(Account author, String title, String description, String content, Date creationDate, Date publicationDate) {
        if (creationDate == null) {
            this.creationDate = new Date();
        } else {
            this.creationDate = creationDate;
        }
        
        this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.publicationDate = publicationDate;
    }
    
    @Override
    public int compareTo(Article other) {
        boolean titlesAreNotNull = this.title != null && other.title != null;
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (titlesAreNotNull) {
            return this.title.compareTo(other.title);
        } else {
            return -1;
        }
    }
}
