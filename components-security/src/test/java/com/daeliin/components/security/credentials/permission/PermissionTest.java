package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.domain.resource.PersistentResource;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(Permission.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenNameIsNull() {
        new Permission("id", Instant.now(), null);
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
