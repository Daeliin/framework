package com.daeliin.framework.commons.security.membership;

import com.daeliin.framework.commons.security.credentials.account.Account;

public interface MembershipNotifications {
    
    /**
     * Notifiy the sign up event.
     * @param account the account which signed up
     */
    void signUp(final Account account);
    
    /**
     * Notifiy the activation event.
     * @param account the account which activated
     */
    void activate(final Account account);
    
    /**
     * Notifiy the new password request event.
     * @param account the account which requested a new password
     */
    void newPassword(final Account account);
    
    /**
     * Notifiy the reset password event.
     * @param account the account which reseted its password
     */
    void resetPassword(final Account account);
}
