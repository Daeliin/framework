package com.daeliin.framework.commons.security.credentials.permission;

import com.daeliin.framework.commons.model.PersistentResource;

public interface Permission extends PersistentResource<Long> {
    
    String getLabel();
    
    void setLabel(final String label);
}
