package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.persistence.resource.service.ResourceService;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.library.AccountLibrary;
import com.daeliin.components.security.library.PermissionLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public final class PermissionServiceTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private PermissionService permissionService;

    @Test
    public void shouldExtendResourceService() {
        assertThat(PermissionService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldFindPermissionsOfAccount() {
        Account account = AccountLibrary.admin();

        Collection<Permission> permissions = permissionService.findForAccount(account.getId());

        assertThat(permissions).containsExactly(PermissionLibrary.admin());
    }
}
