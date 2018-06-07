package com.daeliin.components.webservices.fake;

import com.daeliin.components.core.resource.Conversion;
import com.daeliin.components.webservices.sql.BUuidResource;

public final class UuidResourceConversion implements Conversion<UuidResource, BUuidResource> {

    @Override
    public UuidResource from(BUuidResource bUuidResource) {
        if (bUuidResource == null) {
            return null;
        }

        return new UuidResource(
                bUuidResource.getUuid(),
                bUuidResource.getCreationDate(),
                bUuidResource.getLabel());
    }

    @Override
    public BUuidResource to(UuidResource uuidResource) {
        if (uuidResource == null) {
            return null;
        }

        return new BUuidResource(
                uuidResource.getCreationDate(),
                uuidResource.label,
                uuidResource.getId());
    }
}
