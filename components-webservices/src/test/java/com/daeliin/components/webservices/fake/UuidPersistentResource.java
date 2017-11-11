package com.daeliin.components.webservices.fake;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;

public class UuidPersistentResource extends PersistentResource<String> implements Comparable<UuidPersistentResource> {

    public final String label;

    public UuidPersistentResource(String id, Instant creationDate, String label) {
        super(id, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UuidPersistentResource other) {
        return getId().compareTo(other.getId());
    }
}
