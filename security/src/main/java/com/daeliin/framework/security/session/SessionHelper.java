package com.daeliin.framework.security.session;

import com.daeliin.framework.commons.security.credentials.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {
    
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public SessionHelper(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
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
    
    public org.springframework.security.core.userdetails.UserDetails getCurrentAccount() {
        String currentUsername = getCurrentUsername();
        
        return userDetailsService.loadUserByUsername(currentUsername);
    }
    
    public boolean currentAccountIs(final Account account) {
        boolean currentAccountIsAccount = false;
        org.springframework.security.core.userdetails.UserDetails currentAccount = getCurrentAccount();
        
        if (currentAccount != null && account != null) {
            currentAccountIsAccount = currentAccount.getUsername().equals(account.getUsername());
        }
        
        return currentAccountIsAccount;
    
    }
}
