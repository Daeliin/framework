package com.blebail.components.webservices.fake;


import javax.validation.constraints.NotBlank;
import java.time.Instant;

public final class UuidResourceDto {

    public final String id;
    public final Instant creationDate;

    @NotBlank
    public final String label;

    public UuidResourceDto(String id, Instant creationDate, String label) {
        this.id = id;
        this.creationDate = creationDate;
        this.label = label;
    }
}
