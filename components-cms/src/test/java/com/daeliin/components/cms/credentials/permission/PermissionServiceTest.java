package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.PermissionLibrary;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class PermissionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

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
