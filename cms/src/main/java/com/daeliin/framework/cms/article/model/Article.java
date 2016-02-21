package com.daeliin.framework.cms.article.model;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.daeliin.framework.commons.security.details.UserDetails;
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

@Setter
@Getter
@Entity
@EqualsAndHashCode(of = {"title", "description", "content"}, callSuper = false)
@ToString(of = {"title", "author", "createdDate", "publishedDate"}, callSuper = true)
public abstract class Article<E extends UserDetails> extends LongIdPersistentResource implements Comparable<Article> {
    
    private static final long serialVersionUID = -7808122481259070912L;

    @NotNull
    @OneToOne
    private E author;
    
    @NotBlank
    private String title;
    
    @NotBlank
    @Type(type = "text")
    private String description;
    
    @NotBlank
    @Type(type = "text")
    private String content;

    @NotNull
    @Column(name = "datetime_created")
    private Date createdDate;
    
    @Column(name = "datetime_publised")
    private Date publishedDate;
    
    @NotNull
    private boolean published = false;
}
