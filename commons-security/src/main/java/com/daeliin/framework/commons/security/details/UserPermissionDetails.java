package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;

public interface UserPermissionDetails<ID extends Serializable> extends PersistentResource<ID> {
    
    UserDetails getUser();
    
    PermissionDetails getPermission();
}
