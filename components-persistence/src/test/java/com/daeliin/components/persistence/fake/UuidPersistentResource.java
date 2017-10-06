package com.daeliin.components.persistence.fake;

import com.daeliin.components.core.resource.PersistentResource;

import java.time.Instant;

public class UuidPersistentResource extends PersistentResource<String> implements Comparable<UuidPersistentResource> {

    public final String label;

    public UuidPersistentResource(String uuid, Instant creationDate, String label) {
        super(uuid, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UuidPersistentResource other) {
        return getId().compareTo(other.getId());
    }
}
