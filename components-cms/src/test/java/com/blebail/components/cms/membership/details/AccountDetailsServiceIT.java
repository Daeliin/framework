package com.blebail.components.cms.membership.details;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.cms.credentials.account.AccountService;
import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.cms.library.AccountLibrary;
import com.blebail.components.cms.membership.SignUpRequest;
import com.blebail.components.cms.sql.QAccount;
import com.blebail.components.test.rule.DbFixture;
import com.blebail.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountDetailsServiceIT {

    @Inject
    private AccountService accountService;

    @Inject
    private AccountDetailsService tested;

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
    public void shouldThrowIllegalArgumentException_whenSignUpRequestIsNull() {
        dbFixture.noRollback();

        assertThrows(IllegalArgumentException.class, () -> tested.signUp(null));
    }

    @Test
    public void shouldSignUpAnAccount() {
        SignUpRequest signUpRequest = new SignUpRequest("jane", "jane@blebail.com", "clearPassword");

        Account signedUpAccount = tested.signUp(signUpRequest);

        assertThat(signedUpAccount.id()).isNotNull();
        assertThat(signedUpAccount.creationDate()).isNotNull();
        assertThat(signedUpAccount.username).isEqualTo(signUpRequest.username);
        assertThat(signedUpAccount.email).isEqualTo(signUpRequest.email);
        assertThat(signedUpAccount.password).isNotNull();
        assertThat(signedUpAccount.token).isNotNull();
        assertThat(signedUpAccount.enabled).isFalse();
    }

    @Test
    public void shouldCreateAnAccount_whenSigningUpAnAccount() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("jane", "jane@blebail.com", "clearPassword");

        int accountCountBeforeSignUp = countRows();

        tested.signUp(signUpRequest);

        int accountCountAfterSignUp = countRows();

        assertThat(accountCountAfterSignUp).isEqualTo(accountCountBeforeSignUp + 1);
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenTokenDoesntMatchWhenActivatingIt() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();

        assertThrows(IllegalArgumentException.class, () -> tested.activate(account, "differentToken"));
    }

    @Test
    public void shouldNotActivateAccount_whenTokenDoesntMatch() {
        dbFixture.noRollback();

        Account account = AccountLibrary.inactive();

        try {
            account = tested.activate(account, "differentToken");
        } catch(IllegalArgumentException e) {
        }

        assertThat(account.enabled).isFalse();
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenActivatingIt() {
        Account account = AccountLibrary.admin();

        Account activatedAccount = tested.activate(account, account.token);

        assertThat(activatedAccount.token).isNotBlank();
        assertThat(activatedAccount.token).isNotEqualTo(account.token);
    }

    @Test
    public void shouldActivateAnAccount() {
        Account account = AccountLibrary.inactive();

        Account activatedAccount = tested.activate(account, account.token);

        assertThat(activatedAccount.enabled).isTrue();
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenTokenDoesntMatchWhenResetingPassword() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();

        assertThrows(IllegalArgumentException.class, () -> tested.resetPassword(account, "differentToken", "newPassword"));
    }

    @Test
    public void shouldNotResetPassword_whenTokenDoesntMatch() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();

        try {
            tested.resetPassword(account, "differentToken", "newPassword");
        } catch (IllegalArgumentException e) {
        }

        Account accountAfterResetPasswordTry = accountService.findOne(account.id());

        assertThat(accountAfterResetPasswordTry.password).isEqualTo(account.password);
    }

    @Test
    public void shouldResetPassword() {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = tested.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.password).isNotBlank();
        assertThat(accountAfterResetPassword.password).isNotEqualTo(account.password);
    }

    @Test
    public void shouldAssignANewTokenToAccount_whenResetingItsPassword() {
        Account account = AccountLibrary.admin();

        Account accountAfterResetPassword = tested.resetPassword(account, account.token, "newPassword");

        assertThat(accountAfterResetPassword.token).isNotBlank();
        assertThat(accountAfterResetPassword.token).isNotEqualTo(account.token);
    }

    private int countRows() throws Exception {
        return dbMemory.countRows(QAccount.account.getTableName());
    }
}
