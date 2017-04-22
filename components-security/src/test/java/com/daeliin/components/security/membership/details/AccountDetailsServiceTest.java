package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.daeliin.components.security.exception.InvalidTokenException;
import com.daeliin.components.security.library.AccountLibrary;
import com.daeliin.components.security.membership.SignUpRequest;
import com.daeliin.components.security.sql.QAccount;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class AccountDetailsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountService accountService;

    @Inject
    private AccountDetailsService accountDetailsService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException_whenSignUpRequestIsNull() {
        accountDetailsService.signUp(null);
    }

    @Test
    public void shouldSignUpAnAccount() {
        SignUpRequest signUpRequest = new SignUpRequest("jane", "jane@daeliin.com", "clearPassword");

        Account signedUpAccount = accountDetailsService.signUp(signUpRequest);

        assertThat(signedUpAccount.id()).isNotNull();
        assertThat(signedUpAccount.creationDate()).isNotNull();
        assertThat(signedUpAccount.username).isEqualTo(signUpRequest.username);
        assertThat(signedUpAccount.email).isEqualTo(signUpRequest.email);
        assertThat(signedUpAccount.password).isNotNull();
        assertThat(signedUpAccount.token).isNotNull();
        assertThat(signedUpAccount.enabled).isFalse();
    }

    @Test
    public void shouldCreateAnAccount_whenSigningUpAnAccount() {
        SignUpRequest signUpRequest = new SignUpRequest("jane", "jane@daeliin.com", "clearPassword");

        int accountCountBeforeSignUp = countAccountRows();

        accountDetailsService.signUp(signUpRequest);

        int accountCountAfterSignUp = countAccountRows();

        assertThat(accountCountAfterSignUp).isEqualTo(accountCountBeforeSignUp + 1);
    }

    @Test(expected = InvalidTokenException.class)
    public void shouldThrowInvalidTokenException_whenTokenDoesntMatchWhenActivatingIt() throws Exception {
        Account account = AccountLibrary.admin();

        accountDetailsService.activate(account, "differentToken");
    }

    @Test
    public void shouldNotActivateAccount_whenTokenDoesntMatch() {
        Account account = AccountLibrary.inactive();

        try {
            account = accountDetailsService.activate(account, "differentToken");
        } catch(InvalidTokenException e) {
        }

        assertThat(account.enabled).isFalse();
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenActivatingIt() throws Exception {
        Account account = AccountLibrary.admin();

        Account activatedAccount = accountDetailsService.activate(account, account.token);

        assertThat(activatedAccount.token).isNotBlank();
        assertThat(activatedAccount.token).isNotEqualTo(account.token);
    }

    @Test
    public void shouldActivateAnAccount() throws Exception {
        Account account = AccountLibrary.inactive();

        Account activatedAccount = accountDetailsService.activate(account, account.token);

        assertThat(activatedAccount.enabled).isTrue();
    }

    @Test(expected = InvalidTokenException.class)
    public void shouldThrowInvalidTokenException_whenTokenDoesntMatchWhenResetingPassword() throws Exception {
        Account account = AccountLibrary.admin();

        accountDetailsService.resetPassword(account, "differentToken", "newPassword");
    }

    @Test
    public void shouldNotResetPassword_whenTokenDoesntMatch() {
        Account account = AccountLibrary.admin();
        try {
            accountDetailsService.resetPassword(account, "differentToken", "newPassword");
        } catch (InvalidTokenException e) {
        }

        Account accountAfterResetPasswordTry = accountService.findOne(account.id());

        assertThat(accountAfterResetPasswordTry.password).isEqualTo(account.password);
    }

    @Test
    public void shouldResetPassword() throws Exception {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = accountDetailsService.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.password).isNotBlank();
        assertThat(accountAfterResetPassword.password).isNotEqualTo(account.password);
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenResetingItsPassword() throws Exception {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = accountDetailsService.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.token).isNotBlank();
        assertThat(accountAfterResetPassword.token).isNotEqualTo(account.token);
    }

    private int countAccountRows() {
        return countRowsInTable(QAccount.account.getTableName());
    }
}
