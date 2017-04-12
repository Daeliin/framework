package com.daeliin.components.domain.resource.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UUIDEntity extends PersistentResource {
    private static final long serialVersionUID = 3979928276205727767L;

    public UUIDEntity(Long id, String uuid, LocalDateTime creationDate) {
        super(id, uuid, creationDate);
    }
}
