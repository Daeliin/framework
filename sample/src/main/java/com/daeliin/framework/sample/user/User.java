package com.daeliin.framework.sample.user;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@Entity
@ToString(of = {"name"}, callSuper = true)
public class User extends LongIdPersistentResource {
    
    private static final long serialVersionUID = 5595845507147199458L;
    
    @NotBlank
    private String name;

    public User() {
    }
    
    public User withId(final Long id) {
        this.id = id;
        return this;
    }
    
    public User withName(final String name) {
        this.name = name;
        return this;
    }
}
