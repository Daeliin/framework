package com.daeliin.components.webservices.fake;

import com.daeliin.components.domain.resource.Persistable;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

public final class UuidPersistentResourceDto implements Persistable<String> {

    public final String id;
    public final LocalDateTime creationDate;

    @NotBlank
    public final String label;

    public UuidPersistentResourceDto(String id, LocalDateTime creationDate, String label) {
        this.id = id;
        this.creationDate = creationDate;
        this.label = label;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public LocalDateTime creationDate() {
        return creationDate;
    }
}
