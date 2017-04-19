package com.daeliin.components.domain.resource.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class FakePersistenceResource extends PersistentResource<String> {

    public FakePersistenceResource(String uuid, LocalDateTime creationDate) {
        super(uuid, creationDate);
    }
}
