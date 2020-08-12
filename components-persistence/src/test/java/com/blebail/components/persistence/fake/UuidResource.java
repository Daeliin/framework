package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.PersistentResource;

import java.time.Instant;

public class UuidResource extends PersistentResource<String> implements Comparable<UuidResource> {

    public final String label;

    public UuidResource(String id) {
        super(id);
        this.label = "";
    }

    public UuidResource(String uuid, Instant creationDate) {
        super(uuid, creationDate);
        this.label = "";
    }

    public UuidResource(String uuid, Instant creationDate, String label) {
        super(uuid, creationDate);
        this.label = label;
    }

    @Override
    public int compareTo(UuidResource other) {
        return id().compareTo(other.id());
    }
}
