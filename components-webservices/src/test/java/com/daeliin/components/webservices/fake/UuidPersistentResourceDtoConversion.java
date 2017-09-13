package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.dto.ResourceDtoConversion;

import java.time.Instant;
import java.util.UUID;

public final class UuidPersistentResourceDtoConversion implements ResourceDtoConversion<UuidPersistentResourceDto, UuidPersistentResource, String> {

    @Override
    public UuidPersistentResourceDto instantiate(UuidPersistentResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new UuidPersistentResourceDto(
                uuidEntity.getId(),
                uuidEntity.getCreationDate(),
                uuidEntity.label);
    }

    @Override
    public UuidPersistentResource map(UuidPersistentResourceDto uuidEntityDto, String id, Instant localDateTime) {
        if (uuidEntityDto == null) {
            return null;
        }

        return new UuidPersistentResource(
                id != null ? id: UUID.randomUUID().toString(),
                localDateTime,
                uuidEntityDto.label);
    }
}
