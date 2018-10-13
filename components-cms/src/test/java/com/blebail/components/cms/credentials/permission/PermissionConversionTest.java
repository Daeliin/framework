package com.blebail.components.cms.credentials.permission;

import com.blebail.components.cms.fixtures.PermissionRows;
import com.blebail.components.cms.library.PermissionLibrary;
import com.blebail.components.cms.library.PersistenceConversionTest;
import com.blebail.components.cms.sql.BPermission;
import com.blebail.components.core.resource.Conversion;

public final class PermissionConversionTest extends PersistenceConversionTest<Permission, BPermission> {

    @Override
    protected Conversion<Permission, BPermission> conversion() {
        return new PermissionConversion();
    }

    @Override
    protected Permission object() {
        return PermissionLibrary.admin();
    }

    @Override
    protected BPermission converted() {
        return PermissionRows.admin();
    }
}
