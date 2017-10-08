package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.sql.BPermission;
import com.daeliin.components.core.resource.Conversion;

public final class PermissionConversion implements Conversion<Permission, BPermission> {

    @Override
    public Permission instantiate(BPermission bPermission) {
        return new Permission(
                bPermission.getId(),
                bPermission.getCreationDate(),
                bPermission.getName());
    }

    @Override
    public BPermission map(Permission permission) {
        return new BPermission(
                permission.getCreationDate(),
                permission.getId(),
                permission.name);
    }
}
