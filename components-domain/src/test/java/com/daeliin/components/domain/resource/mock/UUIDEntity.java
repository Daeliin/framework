package com.daeliin.components.domain.resource.mock;

import com.daeliin.components.domain.resource.UUIDPersistentResource;

public class UUIDEntity extends UUIDPersistentResource {
    private static final long serialVersionUID = 3979928276205727767L;
    
    public void setUUID(String UUID) {
        this.uuid = UUID;
    }
}
