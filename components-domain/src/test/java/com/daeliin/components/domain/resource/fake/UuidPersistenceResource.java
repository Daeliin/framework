package com.daeliin.components.domain.resource.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UuidPersistenceResource extends PersistentResource<String> {

    public UuidPersistenceResource(String uuid, LocalDateTime creationDate) {
        super(uuid, creationDate);
    }
}
