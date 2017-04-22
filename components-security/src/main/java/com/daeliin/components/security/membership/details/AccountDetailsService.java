package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.daeliin.components.security.credentials.permission.Permission;
import com.daeliin.components.security.cryptography.Sha512;
import com.daeliin.components.security.cryptography.Token;
import com.daeliin.components.security.exception.InvalidTokenException;
import com.daeliin.components.security.membership.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AccountDetailsService implements UserDetailsService {

    @Inject
    private AccountService accountService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        Account account = accountService.findByUsernameAndEnabled(username);

        if (account == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        Collection<Permission> permissions = accountService.findPermissions(account);
        permissions.forEach(accountPermission -> authorities.add(new SimpleGrantedAuthority(accountPermission.label)));

        return new org.springframework.security.core.userdetails.User(account.username, account.password, authorities);
    }

    public Account signUp(SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            throw new IllegalArgumentException("signUpRequest should not be null");
        }

        AccountEncryption accountEncryption = new AccountEncryption(signUpRequest.username, signUpRequest.clearPassword);

        return accountService.create(new Account(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                signUpRequest.username,
                signUpRequest.email,
                false,
                accountEncryption.password,
                accountEncryption.token));
    }

    public void assignNewToken(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account should not be null");
        }

        Token newToken = new Token(Arrays.asList(account.username), new Sha512(), true);

        Account accountWithNewToken = new Account(
                account.id(),
                account.creationDate(),
                account.username,
                account.email,
                account.enabled,
                account.password,
                newToken.asString);

        accountService.update(accountWithNewToken);
    }

    public void activate(Account account, final String activationToken) throws InvalidTokenException {
        if (!account.token.equals(activationToken)) {
            throw new InvalidTokenException(String.format("Activation token is not valid for %s", account));
        }

        AccountEncryption accountEncryption = new AccountEncryption(account.username, "");

        Account accountWithNewToken = new Account(
                account.id(),
                account.creationDate(),
                account.username,
                account.email,
                true,
                account.password,
                accountEncryption.token);

        accountService.update(accountWithNewToken);
    }

    public void resetPassword(Account account, final String resetPasswordToken, final String newPassword) throws InvalidTokenException {
        if (!account.token.equals(resetPasswordToken)) {
            throw new InvalidTokenException(String.format("Activation token is not valid for %s ", account));
        }

        AccountEncryption accountEncryption = new AccountEncryption(account.username, newPassword);

        Account accountWithNewToken = new Account(
                account.id(),
                account.creationDate(),
                account.username,
                account.email,
                true,
                accountEncryption.password,
                accountEncryption.token);

        accountService.update(accountWithNewToken);
    }
}
