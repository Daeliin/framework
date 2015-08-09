package com.daeliin.framework.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_permission")
public class UserPermission extends LongIdPersistentResource {
    
    private static final long serialVersionUID = -4035977030615510296L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Permission permission;
}
