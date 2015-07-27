package com.daeliin.framework.core.mock;

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
