package com.daeliin.framework.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
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
    
    @JsonIgnore
    private String password;
    
    private boolean enabled = false;

    public User() {
    }
}
