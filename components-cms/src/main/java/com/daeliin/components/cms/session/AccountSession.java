package com.daeliin.components.cms.session;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.membership.details.AccountDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class AccountSession {

    private final AccountDetailsService accountDetailsService;
    private final AccountService accountService;

    @Inject
    public AccountSession(final AccountDetailsService accountDetailsService, AccountService accountService) {
        this.accountDetailsService = accountDetailsService;
        this.accountService = accountService;
    }

    public boolean isLoggedIn() {
        return current() != null;
    }

    public Account current() {
        String currentUsername = username();
        return accountService.findByUsernameAndEnabled(currentUsername);
    }

    public org.springframework.security.core.userdetails.UserDetails currentAccountDetails() {
        String currentUsername = username();

        return accountDetailsService.loadUserByUsername(currentUsername);
    }

    public boolean is(final Account account) {
        boolean currentAccountIsAccount = false;
        org.springframework.security.core.userdetails.UserDetails details = currentAccountDetails();

        if (details != null && account != null) {
            currentAccountIsAccount = details.getUsername().equals(account.username);
        }

        return currentAccountIsAccount;

    }

    public boolean hasPermission(String permission) {
        UserDetails details = currentAccountDetails();

        return details.getAuthorities().stream().anyMatch(p -> p.getAuthority().split("_")[1].toLowerCase().equals(permission.toLowerCase()));
    }

    private String username() {
        String currentUsername = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            currentUsername = authentication.getName();
        }

        return currentUsername;
    }
}
