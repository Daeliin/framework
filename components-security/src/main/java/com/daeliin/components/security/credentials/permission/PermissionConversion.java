package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.security.sql.BPermission;

import java.sql.Timestamp;

public final class PermissionConversion implements Conversion<Permission, BPermission> {

    @Override
    public Permission instantiate(BPermission bPermission) {
        if (bPermission == null) {
            return null;
        }

        return new Permission(
                bPermission.getId(),
                bPermission.getCreationDate().toLocalDateTime(),
                bPermission.getName());
    }

    @Override
    public BPermission map(Permission permission) {
        if (permission == null) {
            return null;
        }

        return new BPermission(
                Timestamp.valueOf(permission.getCreationDate()),
                permission.getId(),
                permission.name);
    }
}
