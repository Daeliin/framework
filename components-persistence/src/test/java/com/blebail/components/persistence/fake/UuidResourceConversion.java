package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.core.resource.Conversion;

public final class UuidResourceConversion implements Conversion<UuidResource, BUuidResource> {

    @Override
    public UuidResource from(BUuidResource bUuidEntity) {
        if (bUuidEntity == null) {
            return null;
        }

        return new UuidResource(
                bUuidEntity.getUuid(),
                bUuidEntity.getCreationDate(),
                bUuidEntity.getLabel());
    }

    @Override
    public BUuidResource to(UuidResource uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new BUuidResource(
                uuidEntity.creationDate(),
                uuidEntity.label,
                uuidEntity.id());
    }
}
