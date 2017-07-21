package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.fixtures.AccountFixtures;
import com.daeliin.components.security.fixtures.PermissionFixtures;
import com.daeliin.components.security.sql.BPermission;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class PermissionRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private PermissionRepository permissionRepository;


    @Test
    public void shouldExtendResourceRepository() {
        assertThat(PermissionRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }

    @Test
    public void shouldReturnEmptyCollection_whenFindingAccountPermissionOfNull() {
        assertThat(permissionRepository.findForAccount(null)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollection_whenAccountHasNoPermission() {
        assertThat(permissionRepository.findForAccount(AccountFixtures.inactive().getId())).isEmpty();
    }

    @Test
    public void shouldFindPermissionsOfAccount() {
        assertThat(permissionRepository.findForAccount(AccountFixtures.admin().getId()))
                .usingFieldByFieldElementComparator()
                .containsOnly(PermissionFixtures.admin());
    }

    @Test
    public void shouldAddPermissionToAccount() {
        permissionRepository.addToAccount(AccountFixtures.admin().getId(), PermissionFixtures.user().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountFixtures.admin().getId());
        assertThat(accountPermissions).usingFieldByFieldElementComparator().containsOnly(PermissionFixtures.admin(), PermissionFixtures.user());
    }

    @Test
    public void shouldNotAddPermissionToAccountTwice() {
        permissionRepository.addToAccount(AccountFixtures.admin().getId(), PermissionFixtures.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountFixtures.admin().getId());
        assertThat(accountPermissions).usingFieldByFieldElementComparator().containsOnly(PermissionFixtures.admin());
    }

    @Test
    public void shouldDeletePermissionForAccount() {
        permissionRepository.deleteForAccount(AccountFixtures.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountFixtures.admin().getId());
        assertThat(accountPermissions).isEmpty();
    }

    @Test
    public void shouldDeleteSpecificPermissionForAccount() {
        permissionRepository.deleteForAccount(AccountFixtures.admin().getId(), PermissionFixtures.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountFixtures.admin().getId());
        assertThat(accountPermissions).isEmpty();
    }
}
