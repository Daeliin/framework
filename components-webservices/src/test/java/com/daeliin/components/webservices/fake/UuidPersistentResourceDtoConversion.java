package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.dto.DtoConversion;

import java.time.LocalDateTime;
import java.util.UUID;

public final class UuidPersistentResourceDtoConversion implements DtoConversion<UuidPersistentResourceDto, UuidPersistentResource, String> {

    @Override
    public UuidPersistentResourceDto instantiate(UuidPersistentResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new UuidPersistentResourceDto(
                uuidEntity.id(),
                uuidEntity.creationDate(),
                uuidEntity.label);
    }

    @Override
    public UuidPersistentResource map(UuidPersistentResourceDto uuidEntityDto, String id, LocalDateTime localDateTime) {
        if (uuidEntityDto == null) {
            return null;
        }

        return new UuidPersistentResource(
                id != null ? id: UUID.randomUUID().toString(),
                localDateTime,
                uuidEntityDto.label);
    }
}
