package com.daeliin.components.cms.membership.details;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.membership.SignUpRequest;
import com.daeliin.components.cms.sql.QAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountDetailsServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountService accountService;

    @Inject
    private AccountDetailsService accountDetailsService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException_whenSignUpRequestIsNull() {
        accountDetailsService.signUp(null);
    }

    @Test
    public void shoudLoadUserByUsername() {
        Account account = AccountLibrary.admin();

        UserDetails userDetails = accountDetailsService.loadUserByUsername(account.username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(account.username);
        assertThat(userDetails.isEnabled()).isEqualTo(account.enabled);
        assertThat(userDetails.getAuthorities().iterator().next()).isEqualTo(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Test
    public void shouldSignUpAnAccount() {
        SignUpRequest signUpRequest = new SignUpRequest("jane", "jane@daeliin.com", "clearPassword");

        Account signedUpAccount = accountDetailsService.signUp(signUpRequest);

        assertThat(signedUpAccount.getId()).isNotNull();
        assertThat(signedUpAccount.getCreationDate()).isNotNull();
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

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException_whenTokenDoesntMatchWhenActivatingIt() {
        Account account = AccountLibrary.admin();

        accountDetailsService.activate(account, "differentToken");
    }

    @Test
    public void shouldNotActivateAccount_whenTokenDoesntMatch() {
        Account account = AccountLibrary.inactive();

        try {
            account = accountDetailsService.activate(account, "differentToken");
        } catch(IllegalArgumentException e) {
        }

        assertThat(account.enabled).isFalse();
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenActivatingIt() {
        Account account = AccountLibrary.admin();

        Account activatedAccount = accountDetailsService.activate(account, account.token);

        assertThat(activatedAccount.token).isNotBlank();
        assertThat(activatedAccount.token).isNotEqualTo(account.token);
    }

    @Test
    public void shouldActivateAnAccount() {
        Account account = AccountLibrary.inactive();

        Account activatedAccount = accountDetailsService.activate(account, account.token);

        assertThat(activatedAccount.enabled).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException_whenTokenDoesntMatchWhenResetingPassword() {
        Account account = AccountLibrary.admin();

        accountDetailsService.resetPassword(account, "differentToken", "newPassword");
    }

    @Test
    public void shouldNotResetPassword_whenTokenDoesntMatch() {
        Account account = AccountLibrary.admin();
        try {
            accountDetailsService.resetPassword(account, "differentToken", "newPassword");
        } catch (IllegalArgumentException e) {
        }

        Account accountAfterResetPasswordTry = accountService.findOne(account.getId());

        assertThat(accountAfterResetPasswordTry.password).isEqualTo(account.password);
    }

    @Test
    public void shouldResetPassword() {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = accountDetailsService.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.password).isNotBlank();
        assertThat(accountAfterResetPassword.password).isNotEqualTo(account.password);
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenResetingItsPassword() {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = accountDetailsService.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.token).isNotBlank();
        assertThat(accountAfterResetPassword.token).isNotEqualTo(account.token);
    }

    private int countAccountRows() {
        return countRowsInTable(QAccount.account.getTableName());
    }
}
