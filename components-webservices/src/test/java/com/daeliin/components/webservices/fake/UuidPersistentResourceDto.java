package com.daeliin.components.webservices.fake;

import org.hibernate.validator.constraints.NotBlank;

import java.time.Instant;

public final class UuidPersistentResourceDto {

    public final String id;
    public final Instant creationDate;

    @NotBlank
    public final String label;

    public UuidPersistentResourceDto(String id, Instant creationDate, String label) {
        this.id = id;
        this.creationDate = creationDate;
        this.label = label;
    }
}
