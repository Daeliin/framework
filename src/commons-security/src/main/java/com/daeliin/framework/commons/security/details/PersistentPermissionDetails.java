package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = {"label"}, callSuper = true)
@ToString(of = {"label"}, callSuper = true)
@MappedSuperclass
public abstract class PersistentPermissionDetails extends LongIdPersistentResource implements PermissionDetails<Long>  {
    
    private static final long serialVersionUID = -4768460974607663983L;
    
    private String label;
}
