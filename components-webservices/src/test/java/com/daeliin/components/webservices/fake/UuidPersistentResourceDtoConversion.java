package com.daeliin.components.webservices.fake;

import com.daeliin.components.domain.resource.Conversion;

public final class UuidPersistentResourceDtoConversion implements Conversion<UuidPersistentResourceDto, UuidPersistentResource> {

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
    public UuidPersistentResource map(UuidPersistentResourceDto uuidEntityDto) {
        if (uuidEntityDto == null) {
            return null;
        }

        return new UuidPersistentResource(
                uuidEntityDto.id(),
                uuidEntityDto.creationDate(),
                uuidEntityDto.label);
    }
}
