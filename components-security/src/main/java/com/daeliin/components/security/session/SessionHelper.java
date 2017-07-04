package com.daeliin.components.security.session;

import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SessionHelper {

    private final UserDetailsService userDetailsService;
    private final AccountService accountService;

    @Inject
    public SessionHelper(final UserDetailsService userDetailsService, AccountService accountService) {
        this.userDetailsService = userDetailsService;
        this.accountService = accountService;
    }

    private String getCurrentUsername() {
        String currentUsername = "";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            currentUsername = authentication.getName();
        }

        return currentUsername;
    }

    public boolean isAccountLoggedIn() {
        return getCurrentAccount() != null;
    }

    public Account getCurrentAccount() {
        String currentUsername = getCurrentUsername();
        return accountService.findByUsernameAndEnabled(currentUsername);
    }

    public org.springframework.security.core.userdetails.UserDetails getCurrentAccountDetails() {
        String currentUsername = getCurrentUsername();

        return userDetailsService.loadUserByUsername(currentUsername);
    }

    public boolean currentAccountIs(final Account account) {
        boolean currentAccountIsAccount = false;
        org.springframework.security.core.userdetails.UserDetails currentAccount = getCurrentAccountDetails();

        if (currentAccount != null && account != null) {
            currentAccountIsAccount = currentAccount.getUsername().equals(account.username);
        }

        return currentAccountIsAccount;

    }
}
