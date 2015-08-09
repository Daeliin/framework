package com.daeliin.framework.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"label"}, callSuper = true)
@Entity
public class Permission extends LongIdPersistentResource {
    
    private static final long serialVersionUID = -8365652154930643212L;
    
    private String label;
}
