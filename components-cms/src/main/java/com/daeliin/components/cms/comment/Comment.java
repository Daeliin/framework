package com.daeliin.components.cms.comment;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import java.time.LocalDateTime;
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
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"author", "content", "creationDate"}, callSuper = true)
@Entity
public class Comment extends UUIDPersistentResource implements Comparable<Comment> {
    
    private static final long serialVersionUID = 7753778128988279460L;
    
    @NotNull
    @OneToOne
    private Account author;
    
    @NotBlank
    @Type(type = "text")
    private String content;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Comment() {
        this.creationDate = LocalDateTime.now();
    }
    
    public Comment(Account author, String content, LocalDateTime additionDate) {
        if (additionDate == null) {
            this.creationDate = LocalDateTime.now();
        } else {
            this.creationDate = additionDate;
        }
        
        this.author = author;
        this.content = content;
    }
    
    @Override
    public int compareTo(Comment other) {
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
