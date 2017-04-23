package com.daeliin.components.core.fake;

import com.daeliin.components.domain.resource.PersistentResource;

import java.time.LocalDateTime;

public class UuidPersistentResource extends PersistentResource<String> implements Comparable<UuidPersistentResource> {

    public final String label;

    public UuidPersistentResource(String uuid, LocalDateTime creationDate, String label) {
        super(uuid, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UuidPersistentResource other) {
        return id.compareTo(other.id);
    }
}
