package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"user", "permission"}, callSuper = true)
@MappedSuperclass
public abstract class PersistentUserPermissionDetails<E extends UserDetails, P extends PermissionDetails> 
    extends LongIdPersistentResource implements UserPermissionDetails<Long>  {
    
    private static final long serialVersionUID = 956187325117395404L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    E user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    P permission;
}
