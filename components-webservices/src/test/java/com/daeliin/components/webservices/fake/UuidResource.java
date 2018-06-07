package com.daeliin.components.webservices.fake;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;

public class UuidResource extends PersistentResource<String> implements Comparable<UuidResource> {

    public final String label;

    public UuidResource(String id, Instant creationDate, String label) {
        super(id, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UuidResource other) {
        return getId().compareTo(other.getId());
    }
}
