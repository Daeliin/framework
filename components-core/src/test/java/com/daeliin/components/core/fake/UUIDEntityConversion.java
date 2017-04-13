package com.daeliin.components.core.fake;

import com.daeliin.components.core.sql.BUuidEntity;
import com.daeliin.components.domain.resource.Conversion;

public final class UUIDEntityConversion implements Conversion<UUIDEntity, BUuidEntity> {

    @Override
    public UUIDEntity instantiate(BUuidEntity conversion) {
        return null;
    }

    @Override
    public BUuidEntity map(UUIDEntity object) {
        return null;
    }
}
