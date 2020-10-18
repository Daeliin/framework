package com.blebail.components.cms.credentials.permission;

import com.blebail.components.cms.fixtures.AccountRows;
import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.cms.fixtures.PermissionRows;
import com.blebail.components.cms.sql.BPermission;
import com.blebail.junit.SqlFixture;
import com.blebail.junit.SqlMemoryDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PermissionRepositoryTest {

    @Inject
    private PermissionRepository permissionRepository;

    @RegisterExtension
    public static SqlMemoryDb sqlMemoryDb = new SqlMemoryDb();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDb::dataSource,
            sequenceOf(
                    JavaFixtures.account(),
                    JavaFixtures.permission(),
                    JavaFixtures.account_permission()
            ));

    @Test
    public void shouldReturnEmptyCollection_whenFindingAccountPermissionOfNull() {
        dbFixture.readOnly();

        assertThat(permissionRepository.findForAccount(null)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollection_whenAccountHasNoPermission() {
        dbFixture.readOnly();

        assertThat(permissionRepository.findForAccount(AccountRows.inactive().getId())).isEmpty();
    }

    @Test
    public void shouldFindPermissionsOfAccount() {
        dbFixture.readOnly();

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
        dbFixture.readOnly();

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
        dbFixture.readOnly();

        boolean isUsed = permissionRepository.isUsed(PermissionRows.admin().getId());

        assertThat(isUsed).isTrue();
    }

    @Test
    public void shouldCheckThatPermissionIsNotUsed() {
        dbFixture.readOnly();

        boolean isUsed = permissionRepository.isUsed("nonExistingPermissionId");

        assertThat(isUsed).isFalse();
    }
}
