package com.daeliin.components.cms.credentials.permission;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PermissionTest {

    @Test
    public void shouldThrowException_whenNameIsNull() {
        assertThrows(Exception.class, () -> new Permission("id", Instant.now(), null));
    }

    @Test
    public void shouldAssignAName() {
        assertThat(new Permission("id", Instant.now(), "ADMIN").name).isEqualTo("ADMIN");
    }

    @Test
    public void shouldPrintsItsName() {
        assertThat(new Permission("id", Instant.now(), "ADMIN").toString()).contains("ADMIN");
    }

    @Test
    public void shouldBeComparedOnNames() {
        Permission adminPermission = new Permission("id1", Instant.now(), "ADMIN");
        Permission userPermission = new Permission("id2", Instant.now(), "USER");

        assertThat(adminPermission.compareTo(userPermission)).isNegative();
        assertThat(userPermission.compareTo(adminPermission)).isPositive();
    }
}
