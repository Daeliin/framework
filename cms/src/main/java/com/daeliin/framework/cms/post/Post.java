package com.daeliin.framework.cms.post;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = {"creationDate", "publicationDate"}, callSuper = false)
@ToString(of = {"creationDate", "publicationDate", "published"}, callSuper = true)
@Entity
public class Post
    extends LongIdPersistentResource implements Comparable<Post> {
    
    private static final long serialVersionUID = -4086446579692925759L;
    
    @Column(name = "creation_datetime")
    private Date creationDate;
    
    @Column(name = "publication_datetime")
    private Date publicationDate;
    
    @NotNull
    private boolean published = false;

    public Post() {
        setCreationDateToCurrentDate();
    }

    public Post(Date creationDate, Date publicationDate) {
        if (creationDate == null) {
            setCreationDateToCurrentDate();
        } else {
            this.creationDate = creationDate;
        }
        
        this.publicationDate = publicationDate;
    }

    @Override
    public int compareTo(Post o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    private void setCreationDateToCurrentDate() {
        this.creationDate = new Date();
    }
}
