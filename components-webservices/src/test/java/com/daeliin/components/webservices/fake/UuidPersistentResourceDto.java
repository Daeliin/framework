package com.daeliin.components.webservices.fake;

import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

public final class UuidPersistentResourceDto {

    public final String id;
    public final LocalDateTime creationDate;

    @NotBlank
    public final String label;

    public UuidPersistentResourceDto(String id, LocalDateTime creationDate, String label) {
        this.id = id;
        this.creationDate = creationDate;
        this.label = label;
    }
}
