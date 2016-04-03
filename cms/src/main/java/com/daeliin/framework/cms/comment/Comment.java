package com.daeliin.framework.cms.comment;

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
@EqualsAndHashCode(of = {"author", "content", "additionDate"}, callSuper = false)
@ToString(of = {"author", "content", "additionDate"}, callSuper = true)
@Entity
public class Comment
    extends LongIdPersistentResource implements Comparable<Comment> {
    
    private static final long serialVersionUID = 7753778128988279460L;
    
    @NotNull
    @OneToOne
    private Account author;
    
    @NotBlank
    @Type(type = "text")
    private String content;
    
    @Column(name = "addition_datetime")
    private Date additionDate;

    public Comment() {
        setAdditionDateToCurrentDate();
    }
    
    public Comment(Account author, String content, Date additionDate) {
        if (additionDate == null) {
            setAdditionDateToCurrentDate();
        } else {
            this.additionDate = additionDate;
        }
        
        this.author = author;
        this.content = content;
    }
    
    @Override
    public int compareTo(Comment other) {
        boolean additionDatesAreNotNull = this.additionDate != null && other.additionDate != null;
        boolean sameAuthor = this.author != null && other.author != null && this.author.equals(other.author);
        boolean sameContent = this.content != null && other.content != null && this.content.equals(other.content);
        boolean sameAdditionDate = additionDatesAreNotNull && this.additionDate.equals(other.additionDate);
        
        if (sameAuthor && sameContent && sameAdditionDate) {
            return 0;
        }
        
        if (additionDatesAreNotNull) {
            return this.additionDate.compareTo(other.additionDate);
        } else {
            return -1;
        }
    }
    
    private void setAdditionDateToCurrentDate() {
        this.additionDate = new Date();
    }
}
