package com.daeliin.components.cms.session;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.membership.details.AccountDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SessionHelper {

    private final AccountDetailsService accountDetailsService;
    private final AccountService accountService;

    @Inject
    public SessionHelper(final AccountDetailsService accountDetailsService, AccountService accountService) {
        this.accountDetailsService = accountDetailsService;
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

        return accountDetailsService.loadUserByUsername(currentUsername);
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
