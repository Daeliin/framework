package com.blebail.components.core.fake;


import java.time.Instant;

public final class ImmutableResource {

    public final String id;
    public final Instant creationDate;
    public final String label;

    public ImmutableResource(String id, Instant creationDate, String label) {
        this.id = id;
        this.creationDate = creationDate;
        this.label = label;
    }
}
