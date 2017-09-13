package com.daeliin.components.domain.resource.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.Instant;

public class UuidPersistentResource extends PersistentResource<String> {

    public UuidPersistentResource(String uuid) {
        super(uuid);
    }

    public UuidPersistentResource(String uuid, Instant creationDate) {
        super(uuid, creationDate);
    }
}
