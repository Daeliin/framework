package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.fixtures.AccountRows;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.fixtures.PermissionRows;
import com.daeliin.components.cms.sql.BPermission;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PermissionRepositoryTest {

    @Inject
    private PermissionRepository permissionRepository;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory,
        sequenceOf(
            JavaFixtures.account(),
            JavaFixtures.permission(),
            JavaFixtures.account_permission()
        )
    );

    @Test
    public void shouldExtendResourceRepository() {
        dbFixture.noRollback();

        assertThat(PermissionRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }

    @Test
    public void shouldReturnEmptyCollection_whenFindingAccountPermissionOfNull() {
        dbFixture.noRollback();

        assertThat(permissionRepository.findForAccount(null)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollection_whenAccountHasNoPermission() {
        dbFixture.noRollback();

        assertThat(permissionRepository.findForAccount(AccountRows.inactive().getId())).isEmpty();
    }

    @Test
    public void shouldFindPermissionsOfAccount() {
        dbFixture.noRollback();

        assertThat(permissionRepository.findForAccount(AccountRows.admin().getId()))
                .usingFieldByFieldElementComparator()
                .containsOnly(PermissionRows.admin());
    }

    @Test
    public void shouldAddPermissionToAccount() {
        permissionRepository.addToAccount(AccountRows.admin().getId(), PermissionRows.user().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountRows.admin().getId());
        assertThat(accountPermissions).usingFieldByFieldElementComparator().containsOnly(PermissionRows.admin(), PermissionRows.user());
    }

    @Test
    public void shouldNotAddPermissionToAccountTwice() {
        dbFixture.noRollback();

        permissionRepository.addToAccount(AccountRows.admin().getId(), PermissionRows.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountRows.admin().getId());
        assertThat(accountPermissions).usingFieldByFieldElementComparator().containsOnly(PermissionRows.admin());
    }

    @Test
    public void shouldDeletePermissionForAccount() {
        permissionRepository.deleteForAccount(AccountRows.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountRows.admin().getId());
        assertThat(accountPermissions).isEmpty();
    }

    @Test
    public void shouldDeleteSpecificPermissionForAccount() {
        permissionRepository.deleteForAccount(AccountRows.admin().getId(), PermissionRows.admin().getId());

        Collection<BPermission> accountPermissions = permissionRepository.findForAccount(AccountRows.admin().getId());
        assertThat(accountPermissions).isEmpty();
    }

    @Test
    public void shouldCheckThatPermissionIsUsed() {
        dbFixture.noRollback();

        boolean isUsed = permissionRepository.isUsed(PermissionRows.admin().getId());

        assertThat(isUsed).isTrue();
    }

    @Test
    public void shouldCheckThatPermissionIsNotUsed() {
        dbFixture.noRollback();

        boolean isUsed = permissionRepository.isUsed("nonExistingPermissionId");

        assertThat(isUsed).isFalse();
    }
}
