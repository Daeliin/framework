package com.daeliin.framework.security.session;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.security.details.PersistentUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionHelper {
    
    @Autowired
    protected PersistentUserDetailsService userDetailsService;
    
    private String getCurrentUsername() {
        String currentUsername = "";
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            currentUsername = authentication.getName();
        }
        
        return currentUsername;
    }
    
    public boolean isUserLoggedIn() {
        return getCurrentUserDetails() != null;
    }
    
    public org.springframework.security.core.userdetails.UserDetails getCurrentUserDetails() {
        String currentUsername = getCurrentUsername();
        
        return (org.springframework.security.core.userdetails.UserDetails)userDetailsService.loadUserByUsername(currentUsername);
    }
    
    public boolean currentUserIs(final UserDetails userDetails) {
        boolean currentUserIsUser = false;
        org.springframework.security.core.userdetails.UserDetails currentUserDetails = getCurrentUserDetails();
        
        if (currentUserDetails != null && userDetails != null) {
            currentUserIsUser = currentUserDetails.getUsername().equals(userDetails.getUsername());
        }
        
        return currentUserIsUser;
    }
}
