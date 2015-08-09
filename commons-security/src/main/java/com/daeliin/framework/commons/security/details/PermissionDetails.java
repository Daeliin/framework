package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;

public interface PermissionDetails<ID extends Serializable> extends PersistentResource<ID> {
    
    String getLabel();
    
    void setLabel(final String label);
}
