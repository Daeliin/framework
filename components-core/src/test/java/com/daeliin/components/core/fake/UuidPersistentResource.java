package com.daeliin.components.core.fake;

import com.daeliin.components.domain.resource.PersistentResource;

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
