package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.Application;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class PermissionRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(PermissionRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}
