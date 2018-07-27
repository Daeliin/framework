package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.fixtures.PermissionRows;
import com.daeliin.components.cms.library.PermissionLibrary;
import com.daeliin.components.cms.library.PersistentConversionTest;
import com.daeliin.components.cms.sql.BPermission;
import com.daeliin.components.core.resource.Conversion;

public final class PermissionConversionTest extends PersistentConversionTest<Permission, BPermission> {

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
