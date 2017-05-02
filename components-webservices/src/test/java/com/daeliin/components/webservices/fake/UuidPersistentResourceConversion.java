package com.daeliin.components.webservices.fake;

import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.webservices.sql.BUuidPersistentResource;

import java.sql.Timestamp;

public final class UuidPersistentResourceConversion implements Conversion<UuidPersistentResource, BUuidPersistentResource> {

    @Override
    public UuidPersistentResource instantiate(BUuidPersistentResource bUuidEntity) {
        if (bUuidEntity == null) {
            return null;
        }

        return new UuidPersistentResource(
                bUuidEntity.getUuid(),
                bUuidEntity.getCreationDate().toLocalDateTime(),
                bUuidEntity.getLabel());
    }

    @Override
    public BUuidPersistentResource map(UuidPersistentResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new BUuidPersistentResource(
                Timestamp.valueOf(uuidEntity.creationDate()),
                uuidEntity.label,
                uuidEntity.id());
    }
}
