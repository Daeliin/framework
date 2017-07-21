package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.library.AccountLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountService accountService;


    @Test
    public void shouldExtendResourceService() {
        assertThat(AccountService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test
    public void shouldFindAnEnabledAccount_byUsername() {
        Account account = AccountLibrary.admin();
        assertThat(accountService.findByUsernameAndEnabled(account.username)).isEqualTo(account);
    }

    @Test
    public void shouldReturnNull_whenFindingEnabledAccountByUsername_ifAccountIsDisabled() {
        assertThat(accountService.findByUsernameAndEnabled(AccountLibrary.inactive().username)).isEqualTo(null);
    }

    @Test
    public void shouldCheckThatUsernameDoesntExists() {
        assertThat(accountService.usernameExists("nonExistingUsername")).isFalse();
    }

    @Test
    public void shouldCheckThatUsernameAlreadyExists() {
        assertThat(accountService.usernameExists(AccountLibrary.admin().username)).isTrue();
    }
}