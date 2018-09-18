package com.daeliin.components.cms.membership.details;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.membership.SignUpRequest;
import com.daeliin.components.core.resource.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;

import static java.util.Objects.requireNonNull;

@Service
public class AccountDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsService.class);

    private final AccountService accountService;

    @Inject
    public AccountDetailsService(AccountService accountService) {
        this.accountService = requireNonNull(accountService);
    }

    public Account signUp(SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            throw new IllegalArgumentException("signUpRequest should not be null");
        }

        AccountEncryption accountEncryption = new AccountEncryption(signUpRequest.username, signUpRequest.clearPassword);

        return accountService.create(new Account(
                Id.random(),
                Instant.now(),
                signUpRequest.username,
                signUpRequest.email,
                false,
                accountEncryption.password,
                accountEncryption.token));
    }

    public Account activate(Account account, final String activationToken) {
        if (!account.token.equals(activationToken)) {
            LOGGER.warn(String.format("an attempt to activate %s with an invalid token[%s] has been made", account, activationToken));
            throw new IllegalArgumentException(String.format("Activation token is not valid for %s", account));
        }

        AccountEncryption accountEncryption = new AccountEncryption(account.username, "");

        Account accountWithNewToken = new Account(
                account.getId(),
                account.getCreationDate(),
                account.username,
                account.email,
                true,
                account.password,
                accountEncryption.token);

        return accountService.update(accountWithNewToken);
    }

    public Account resetPassword(Account account, final String resetPasswordToken, final String newPassword) {
        if (!account.token.equals(resetPasswordToken)) {
            LOGGER.warn(String.format("an attempt to reset %s password with an invalid token[%s] has been made", account, resetPasswordToken));
            throw new IllegalArgumentException(String.format("Activation token is not valid for %s ", account));
        }

        AccountEncryption accountEncryption = new AccountEncryption(account.username, newPassword);

        Account accountWithNewToken = new Account(
                account.getId(),
                account.getCreationDate(),
                account.username,
                account.email,
                true,
                accountEncryption.password,
                accountEncryption.token);

        return accountService.update(accountWithNewToken);
    }
}
