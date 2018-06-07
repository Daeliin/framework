package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.dto.ResourceDtoConversion;

import java.time.Instant;
import java.util.UUID;

public final class UuidResourceDtoConversion implements ResourceDtoConversion<UuidResourceDto, UuidResource, String> {

    @Override
    public UuidResourceDto instantiate(UuidResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new UuidResourceDto(
                uuidEntity.getId(),
                uuidEntity.getCreationDate(),
                uuidEntity.label);
    }

    @Override
    public UuidResource map(UuidResourceDto uuidEntityDto, String id, Instant localDateTime) {
        if (uuidEntityDto == null) {
            return null;
        }

        return new UuidResource(
                id != null ? id: UUID.randomUUID().toString(),
                localDateTime,
                uuidEntityDto.label);
    }
}
