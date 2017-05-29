package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.service.ResourceService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionServiceTest {

    @Test
    public void shouldExtendResourceService() {
        assertThat(PermissionService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }
}
