package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.fixtures.PermissionRows;
import com.daeliin.components.cms.library.PermissionLibrary;
import com.daeliin.components.cms.sql.BPermission;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionConversionTest {

    private PermissionConversion permissionConversion = new PermissionConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        Permission nullPermission = null;

        permissionConversion.to(nullPermission);
    }

    @Test
    public void shouldMapPermission() {
        BPermission mappedPermission = permissionConversion.to(PermissionLibrary.admin());

        assertThat(mappedPermission).isEqualToComparingFieldByField(PermissionRows.admin());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BPermission nullPermissionRow = null;

        permissionConversion.from(nullPermissionRow);
    }

    @Test
    public void shouldInstantiateAnPermission() {
        Permission rebuiltPermission = permissionConversion.from(PermissionRows.admin());

        assertThat(rebuiltPermission).isEqualTo(PermissionLibrary.admin());
    }
}