package com.daeliin.components.security.membership;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.daeliin.components.security.exception.AccountAlreadyExistException;
import com.daeliin.components.security.exception.InvalidTokenException;
import com.daeliin.components.security.library.AccountLibrary;
import com.daeliin.components.security.sql.QAccount;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = Application.class)
public class MembershipServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private MembershipService membershipService;

    @Inject
    private AccountService accountService;

    @Test(expected = AccountAlreadyExistException.class)
    public void shouldThrowException_whenSigningUpExistingAccount() {
        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        membershipService.signUp(signUpRequest);
    }

    @Test
    public void signup_existingAccount_doesntCreateAccount() {
        Account existingAccount = AccountLibrary.admin();
        SignUpRequest signUpRequest = new SignUpRequest(existingAccount.username, existingAccount.email, "password");

        int accountCountBeforeSignUp = countAccountRows();

        try {
            membershipService.signUp(signUpRequest);
        } catch (AccountAlreadyExistException e) {
        }

        int accountCountAfterSignUp = countAccountRows();

        assertEquals(accountCountAfterSignUp, accountCountBeforeSignUp);
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowException_whenActivatingNonExistentAccountId() throws InvalidTokenException {
        membershipService.activate("AOADAZD-65454", "ok");
    }

    @Test(expected = InvalidTokenException.class)
    public void shouldThrowException_whenTokenDoesntMatchWhenActivatingAccount() throws Exception {
        Account account = AccountLibrary.inactive();

        membershipService.activate(account.id(), "wrongToken");
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowException_whenRequestingANewPasswordForNonExistingAccount() throws Exception {
        membershipService.newPassword("AFEZAFEZ-6544");
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowException_whenResetingPasswordForNonExistingAccount() throws Exception {
        membershipService.resetPassword("AFEZAFEZ-6544", "token", "newPassword");
    }

    @Test(expected = InvalidTokenException.class)
    public void shouldThrowException_whenTokenDoesntMatchWhenResetingPassword() throws Exception {
        Account account = AccountLibrary.admin();
        membershipService.resetPassword(account.id(), "wrongToken", "newPassword");
    }

    private int countAccountRows() {
        return countRowsInTable(QAccount.account.getTableName());
    }
}
