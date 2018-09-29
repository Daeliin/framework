package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountServiceIT {

    @Inject
    private AccountService accountService;

    @Inject
    private PermissionService permissionService;

    @RegisterExtension
    public static DbMemory dbMemory = new DbMemory();

    @RegisterExtension
    public DbFixture dbFixture = new DbFixture(dbMemory,
            sequenceOf(
                    JavaFixtures.account(),
                    JavaFixtures.permission(),
                    JavaFixtures.account_permission()
            )
    );

    @Test
    public void shoudLoadUserByUsername() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();

        UserDetails userDetails = accountService.loadUserByUsername(account.username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(account.username);
        assertThat(userDetails.isEnabled()).isEqualTo(account.enabled);
        assertThat(userDetails.getAuthorities().iterator().next()).isEqualTo(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    public void shouldFindAnAccount_byUsername() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsername(account.username)).isEqualTo(account);
    }

    @Test
    public void shouldThrowException_whenFindingAnAccountByUsername_ifUsernameDoesntExist() {
        dbFixture.noRollback();

        assertThrows(NoSuchElementException.class, () -> accountService.findByUsername("nonExistingUsername"));
    }

    @Test
    public void shouldFindAnEnabledAccount_byUsername() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsernameAndEnabled(account.username)).isEqualTo(account);
    }

    @Test
    public void shouldThrowException_whenFindingEnabledAccountByUsername_ifAccountIsDisabled() {
        dbFixture.noRollback();

        assertThrows(NoSuchElementException.class, () -> accountService.findByUsernameAndEnabled(AccountLibrary.inactive().username));
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
        accountService.delete(AccountLibrary.inactive().id());

        Collection<Permission> accountPermissions = permissionService.findForAccount(AccountLibrary.inactive().id());

        assertThat(accountPermissions).isEmpty();
    }
}