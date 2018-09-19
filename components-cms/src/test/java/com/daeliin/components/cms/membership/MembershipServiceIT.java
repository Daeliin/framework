package com.daeliin.components.cms.membership;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.sql.QAccount;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MembershipServiceIT {

    @Inject
    private MembershipService membershipService;

    @Inject
    private AccountService accountService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.account());

    @Test(expected = IllegalStateException.class)
    public void shouldThrowException_whenSigningUpExistingAccount() {
        dbFixture.noRollback();

        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        membershipService.signUp(signUpRequest);
    }

    @Test
    public void shouldNotCreateAccount_whenSigningUpExistingAccount() throws Exception {
        dbFixture.noRollback();

        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        int accountCountBeforeSignUp = countAccountRows();

        try {
            membershipService.signUp(signUpRequest);
        } catch (IllegalStateException e) {
        }

        int accountCountAfterSignUp = countAccountRows();

        assertThat(accountCountAfterSignUp).isEqualTo(accountCountBeforeSignUp);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenActivatingNonExistentAccountId() {
        dbFixture.noRollback();

        membershipService.activate("AOADAZD-65454", "ok");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenTokenDoesntMatchWhenActivatingAccount() {
        dbFixture.noRollback();

        Account account = AccountLibrary.inactive();

        membershipService.activate(account.id(), "wrongToken");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenRequestingANewPasswordForNonExistingAccount() {
        dbFixture.noRollback();

        membershipService.newPassword("AFEZAFEZ-6544");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenResetingPasswordForNonExistingAccount() {
        dbFixture.noRollback();

        membershipService.resetPassword("AFEZAFEZ-6544", "token", "newPassword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenTokenDoesntMatchWhenResetingPassword() {
        dbFixture.noRollback();

        Account account = AccountLibrary.admin();
        membershipService.resetPassword(account.id(), "wrongToken", "newPassword");
    }

    private int countAccountRows() throws Exception {
        return dbMemory.countRows(QAccount.account.getTableName());
    }
}
