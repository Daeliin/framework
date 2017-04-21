package com.daeliin.components.security.credentials.permission;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionTest {

    @Test(expected = Exception.class)
    public void shouldThrowException_whenLabelIsNull() {
        new Permission(null);
    }

    @Test
    public void shouldAssignALabel() {
        assertThat(new Permission("ADMIN").label).isEqualTo("ADMIN");
    }

    @Test
    public void shouldBeEqualToOtherInstance_withSameLabel() {
        Permission permission = new Permission("ADMIN");
        Permission samePermission = new Permission("ADMIN");

        assertThat(permission).isEqualTo(samePermission);
    }

    @Test
    public void shouldPrintsItsLabel() {
        assertThat(new Permission("ADMIN").toString()).contains("ADMIN");
    }

    @Test
    public void shouldBeComparedOnLabels() {
        Permission adminPermission = new Permission("ADMIN");
        Permission userPermission = new Permission("USER");

        assertThat(adminPermission.compareTo(userPermission)).isNegative();
        assertThat(userPermission.compareTo(adminPermission)).isPositive();
    }
}
