package com.daeliin.components.cms.membership.details;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.exception.InvalidTokenException;
import com.daeliin.components.cms.membership.SignUpRequest;
import com.daeliin.components.core.resource.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Inject
    private AccountService accountService;

    @Inject
    private PermissionService permissionService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        Account account = accountService.findByUsernameAndEnabled(username);

        if (account == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        Collection<Permission> permissions = permissionService.findForAccount(account.getId());
        permissions.forEach(accountPermission -> authorities.add(new SimpleGrantedAuthority("ROLE_" + accountPermission.getId())));

        return new org.springframework.security.core.userdetails.User(account.username, account.password, authorities);
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

    public Account activate(Account account, final String activationToken) throws InvalidTokenException {
        if (!account.token.equals(activationToken)) {
            throw new InvalidTokenException(String.format("Activation token is not valid for %s", account));
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

    public Account resetPassword(Account account, final String resetPasswordToken, final String newPassword) throws InvalidTokenException {
        if (!account.token.equals(resetPasswordToken)) {
            throw new InvalidTokenException(String.format("Activation token is not valid for %s ", account));
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
