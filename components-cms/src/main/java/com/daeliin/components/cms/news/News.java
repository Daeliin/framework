package com.daeliin.components.cms.news;

import com.daeliin.components.cms.article.Article;
import com.daeliin.components.domain.resource.PersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"article", "author", "content", "creationDate"}, callSuper = true)
@Entity
public class News extends PersistentResource implements Comparable<News> {

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
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    private String source;
    
    public News() {
        this.creationDate = LocalDateTime.now();
    }

    public News(Article article, Account author, String content, LocalDateTime creationDate) {
        if (creationDate == null) {
            this.creationDate = LocalDateTime.now();
        }
        
        this.article = article;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
    }
    
    @Override
    public int compareTo(News other) {
        boolean creationDatesAreNotNull = this.creationDate != null && other.creationDate != null;
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (creationDatesAreNotNull) {
            return this.creationDate.compareTo(other.creationDate);
        } else {
            return -1;
        }
    }
}
