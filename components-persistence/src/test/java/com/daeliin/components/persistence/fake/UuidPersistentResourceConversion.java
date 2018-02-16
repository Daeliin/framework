package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.sql.BUuidPersistentResource;
import com.daeliin.components.core.resource.Conversion;

public final class UuidPersistentResourceConversion implements Conversion<UuidPersistentResource, BUuidPersistentResource> {

    @Override
    public UuidPersistentResource from(BUuidPersistentResource bUuidEntity) {
        if (bUuidEntity == null) {
            return null;
        }

        return new UuidPersistentResource(
                bUuidEntity.getUuid(),
                bUuidEntity.getCreationDate(),
                bUuidEntity.getLabel());
    }

    @Override
    public BUuidPersistentResource to(UuidPersistentResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new BUuidPersistentResource(
                uuidEntity.getCreationDate(),
                uuidEntity.label,
                uuidEntity.getId());
    }
}
