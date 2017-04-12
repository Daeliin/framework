package com.daeliin.components.core.fake;

import com.daeliin.components.core.sql.BUuidEntity;
import com.daeliin.components.domain.resource.Conversion;

import java.sql.Timestamp;

public final class UUIDEntityConversion implements Conversion<UUIDEntity, BUuidEntity> {

    @Override
    public UUIDEntity instantiate(BUuidEntity bUuidEntity) {
        if (bUuidEntity == null) {
            return null;
        }

        return new UUIDEntity(
                bUuidEntity.getId(),
                bUuidEntity.getUuid(),
                bUuidEntity.getCreationDate().toLocalDateTime(),
                bUuidEntity.getLabel());
    }

    @Override
    public BUuidEntity map(UUIDEntity uuidEntity) {
        if (uuidEntity == null) {
            return null;
        }

        return new BUuidEntity(
                Timestamp.valueOf(uuidEntity.creationDate()),
                uuidEntity.id(),
                uuidEntity.label,
                uuidEntity.uuid());
    }
}
