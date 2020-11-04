package com.blebail.components.cms.membership;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.cms.credentials.account.AccountService;
import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.cms.library.AccountLibrary;
import com.blebail.junit.SqlFixture;
import com.blebail.junit.SqlMemoryDb;
import com.blebail.querydsl.crud.QAccount;
import com.blebail.querydsl.crud.commons.utils.Factories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MembershipServiceIT {

    @Inject
    private AccountService accountService;

    @Inject
    private MembershipService tested;

    @RegisterExtension
    public static SqlMemoryDb sqlMemoryDb = new SqlMemoryDb();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDb::dataSource, JavaFixtures.account());

    @Test
    public void shouldThrowException_whenSigningUpExistingAccount() {
        dbFixture.readOnly();

        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        assertThrows(IllegalStateException.class, () -> tested.signUp(signUpRequest));
    }

    @Test
    public void shouldNotCreateAccount_whenSigningUpExistingAccount() {
        dbFixture.readOnly();

        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        long accountCountBeforeSignUp = countAccountRows();

        try {
            tested.signUp(signUpRequest);
        } catch (IllegalStateException e) {
        }

        long accountCountAfterSignUp = countAccountRows();

        assertThat(accountCountAfterSignUp).isEqualTo(accountCountBeforeSignUp);
    }

    @Test
    public void shouldThrowException_whenActivatingNonExistentAccountId() {
        dbFixture.readOnly();

        assertThrows(NoSuchElementException.class, () -> tested.activate("AOADAZD-65454", "ok"));
    }

    @Test
    public void shouldThrowException_whenTokenDoesntMatchWhenActivatingAccount() {
        dbFixture.readOnly();

        Account account = AccountLibrary.inactive();

        assertThrows(IllegalArgumentException.class, () -> tested.activate(account.id(), "wrongToken"));
    }

    @Test
    public void shouldThrowException_whenRequestingANewPasswordForNonExistingAccount() {
        dbFixture.readOnly();

        assertThrows(NoSuchElementException.class, () -> tested.newPassword("AFEZAFEZ-6544"));
    }

    @Test
    public void shouldThrowException_whenResetingPasswordForNonExistingAccount() {
        dbFixture.readOnly();

        assertThrows(NoSuchElementException.class, () -> tested.resetPassword("AFEZAFEZ-6544", "token", "newPassword"));
    }

    @Test
    public void shouldThrowException_whenTokenDoesntMatchWhenResetingPassword() {
        dbFixture.readOnly();

        Account account = AccountLibrary.admin();
        assertThrows(IllegalArgumentException.class, () -> tested.resetPassword(account.id(), "wrongToken", "newPassword"));
    }

    private long countAccountRows() {
        return Factories.defaultQueryFactory(sqlMemoryDb.dataSource())
                .select(QAccount.account)
                .from(QAccount.account)
                .fetchCount();
    }
}
