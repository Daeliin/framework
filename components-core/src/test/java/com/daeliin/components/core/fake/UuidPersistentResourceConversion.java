package com.daeliin.components.core.fake;

import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.domain.resource.Conversion;

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
                Timestamp.valueOf(uuidEntity.getCreationDate()),
                uuidEntity.label,
                uuidEntity.getId());
    }
}
