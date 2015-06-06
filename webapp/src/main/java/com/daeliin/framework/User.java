package com.daeliin.framework;

import com.daeliin.framework.commons.model.PersistentResource;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class User implements PersistentResource {
    
    private static final long serialVersionUID = -1144128630028374773L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotBlank
    private String name;

    public User() {
    }
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
