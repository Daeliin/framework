package com.blebail.components.cms.credentials.permission;

import com.blebail.components.cms.sql.BPermission;
import com.blebail.components.core.resource.Conversion;

public final class PermissionConversion implements Conversion<Permission, BPermission> {

    @Override
    public Permission from(BPermission bPermission) {
        return new Permission(
                bPermission.getId(),
                bPermission.getCreationDate(),
                bPermission.getName());
    }

    @Override
    public BPermission to(Permission permission) {
        return new BPermission(
                permission.creationDate(),
                permission.id(),
                permission.name);
    }
}
