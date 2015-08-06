package com.daeliin.framework.sample.user;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString(of = {"username"}, callSuper = true)
@Entity
public class User extends LongIdPersistentResource {
    
    private static final long serialVersionUID = 5595845507147199458L;
    
    @NotBlank
    private String username;
    
    private String password;
    
    private boolean enabled = false;

    public User() {
    }
}
