package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountService accountService;

    @Inject
    private PermissionService permissionService;

    @Test
    public void shouldExtendResourceService() {
        assertThat(AccountService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldFindAnAccount_byUsername() {
        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsername(account.username)).isEqualTo(account);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingAnAccountByUsername_ifUsernameDoesntExist() {
        accountService.findByUsername("nonExistingUsername");
    }

    @Test
    public void shouldFindAnEnabledAccount_byUsername() {
        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsernameAndEnabled(account.username)).isEqualTo(account);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingEnabledAccountByUsername_ifAccountIsDisabled() {
        accountService.findByUsernameAndEnabled(AccountLibrary.inactive().username);
    }

    @Test
    public void shouldCheckThatUsernameDoesntExists() {
        assertThat(accountService.usernameExists("nonExistingUsername")).isFalse();
    }

    @Test
    public void shouldCheckThatUsernameAlreadyExists() {
        assertThat(accountService.usernameExists(AccountLibrary.admin().username)).isTrue();
    }

    @Test
    public void shouldDeleteAllPermissionsOfAccount() {
        accountService.delete(AccountLibrary.inactive().getId());

        Collection<Permission> accountPermissions = permissionService.findForAccount(AccountLibrary.inactive().getId());

        assertThat(accountPermissions).isEmpty();
    }
}