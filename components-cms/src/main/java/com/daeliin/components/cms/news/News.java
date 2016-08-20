package com.daeliin.components.cms.news;

import com.daeliin.components.cms.article.Article;
import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@ToString(of = {"article", "author", "content", "creationDate"}, callSuper = true)
@Entity
public class News extends UUIDPersistentResource implements Comparable<News> {

    private static final long serialVersionUID = -4011303518879793138L;
    
    @NotNull
    @ManyToOne
    private Article article;
    
    @NotNull
    @OneToOne
    private Account author;
    
    @NotBlank
    @Type(type = "text")
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    
    public News() {
        this.creationDate = new Date();
    }

    public News(Article article, Account author, String content, Date creationDate) {
        if (creationDate == null) {
            this.creationDate = new Date();
        }
        
        this.article = article;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
    }
    
    @Override
    public int compareTo(News other) {
        boolean creationDatesAreNotNull = this.creationDate != null && other.creationDate != null;
        boolean sameArticle = this.article != null && other.article != null && this.article.equals(other.article);
        boolean sameAuthor = this.author != null && other.author != null && this.author.equals(other.author);
        boolean sameContent = this.content != null && other.content != null && this.content.equals(other.content);
        boolean sameCreationDate = creationDatesAreNotNull && this.creationDate.equals(other.creationDate);
        
        if (sameArticle && sameAuthor && sameContent && sameCreationDate) {
            return 0;
        }
        
        if (creationDatesAreNotNull) {
            return this.creationDate.compareTo(other.creationDate);
        } else {
            return -1;
        }
    }
}
