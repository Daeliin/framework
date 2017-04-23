package com.daeliin.components.domain.resource.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UuidPersistentResource extends PersistentResource<String> {

    public UuidPersistentResource(String uuid, LocalDateTime creationDate) {
        super(uuid, creationDate);
    }
}
