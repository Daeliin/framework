package com.daeliin.framework.cms.article;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.daeliin.framework.commons.security.credentials.account.Account;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of = {"title", "description", "content"}, callSuper = false)
@ToString(of = {"title", "author", "creationDate", "publicationDate"}, callSuper = true)
@Entity
public class Article extends LongIdPersistentResource implements Comparable<Article> {
    
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

    @Column(name = "creation_datetime")
    private Date creationDate;
    
    @Column(name = "publication_datetime")
    private Date publicationDate;
    
    @NotNull
    private boolean published = false;

    public Article() {
        setCreationDateToCurrentDate();
    }
    
    public Article(Account author, String title, String description, String content, Date creationDate, Date publicationDate) {
        if (creationDate == null) {
            setCreationDateToCurrentDate();
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
        boolean sameTitle = titlesAreNotNull && this.title.equals(other.title);
        boolean sameDescription = this.description != null && other.description != null && this.description.equals(other.description);
        boolean sameContent = this.content != null && other.content != null && this.content.equals(other.content);
        
        if (sameTitle && sameDescription && sameContent) {
            return 0;
        }
        
        if (titlesAreNotNull) {
            return this.title.compareTo(other.title);
        } else {
            return -1;
        }
    }
    
    private void setCreationDateToCurrentDate() {
        this.creationDate = new Date();
    }
}
