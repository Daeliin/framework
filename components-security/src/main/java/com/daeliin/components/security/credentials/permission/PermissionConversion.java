package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.Conversion;
import com.daeliin.components.security.sql.BPermission;

public final class PermissionConversion implements Conversion<Permission, BPermission> {

    @Override
    public Permission instantiate(BPermission bPermission) {
        if (bPermission == null) {
            return null;
        }

        return new Permission(
                bPermission.getId(),
                bPermission.getCreationDate(),
                bPermission.getName());
    }

    @Override
    public BPermission map(Permission permission) {
        if (permission == null) {
            return null;
        }

        return new BPermission(
                permission.getCreationDate(),
                permission.getId(),
                permission.name);
    }
}
