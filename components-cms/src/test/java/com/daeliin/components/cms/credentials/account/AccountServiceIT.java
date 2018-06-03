package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.persistence.resource.service.ResourceService;
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
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountServiceIT {

    @Inject
    private AccountService accountService;

    @Inject
    private PermissionService permissionService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.account());

    @Test
    public void shouldExtendResourceService() {
        dbFixture.noRollback();

        assertThat(AccountService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldFindAnAccount_byUsername() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsername(account.username)).isEqualTo(account);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingAnAccountByUsername_ifUsernameDoesntExist() {
        dbFixture.noRollback();

        accountService.findByUsername("nonExistingUsername");
    }

    @Test
    public void shouldFindAnEnabledAccount_byUsername() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsernameAndEnabled(account.username)).isEqualTo(account);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingEnabledAccountByUsername_ifAccountIsDisabled() {
        dbFixture.noRollback();

        accountService.findByUsernameAndEnabled(AccountLibrary.inactive().username);
    }

    @Test
    public void shouldCheckThatUsernameDoesntExists() {
        dbFixture.noRollback();

        assertThat(accountService.usernameExists("nonExistingUsername")).isFalse();
    }

    @Test
    public void shouldCheckThatUsernameAlreadyExists() {
        dbFixture.noRollback();

        assertThat(accountService.usernameExists(AccountLibrary.admin().username)).isTrue();
    }

    @Test
    public void shouldDeleteAllPermissionsOfAccount() {
        accountService.delete(AccountLibrary.inactive().getId());

        Collection<Permission> accountPermissions = permissionService.findForAccount(AccountLibrary.inactive().getId());

        assertThat(accountPermissions).isEmpty();
    }
}