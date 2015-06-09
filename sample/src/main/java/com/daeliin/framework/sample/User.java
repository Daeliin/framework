package com.daeliin.framework.sample;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@Entity
public class User extends LongIdPersistentResource {
    
    @NotBlank
    private String name;

    public User() {
    }
}
