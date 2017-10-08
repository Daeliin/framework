package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.fixtures.PermissionFixtures;
import com.daeliin.components.cms.library.PermissionLibrary;
import com.daeliin.components.cms.sql.BPermission;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionConversionTest {

    private PermissionConversion permissionConversion = new PermissionConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        Permission nullPermission = null;

        permissionConversion.map(nullPermission);
    }

    @Test
    public void shouldMapPermission() {
        BPermission mappedPermission = permissionConversion.map(PermissionLibrary.admin());

        assertThat(mappedPermission).isEqualToComparingFieldByField(PermissionFixtures.admin());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BPermission nullPermissionRow = null;

        permissionConversion.instantiate(nullPermissionRow);
    }

    @Test
    public void shouldInstantiateAnPermission() {
        Permission rebuiltPermission = permissionConversion.instantiate(PermissionFixtures.admin());

        assertThat(rebuiltPermission).isEqualTo(PermissionLibrary.admin());
    }
}