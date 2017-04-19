package com.daeliin.components.core.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UuidPersistentResource extends PersistentResource<String> {

    public final String label;

    public UuidPersistentResource(String uuid, LocalDateTime creationDate, String label) {
        super(uuid, creationDate);
        this.label = label;
    }
}
