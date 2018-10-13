package com.blebail.components.cms.membership.notifications;

import com.blebail.components.cms.credentials.account.Account;


public interface MembershipNotifications {
    
    /**
     * Notifies the sign up event.
     * @param account the account which signed up
     */
    void signUp(final Account account);
    
    /**
     * Notifies the activation event.
     * @param account the account which activated
     */
    void activate(final Account account);
    
    /**
     * Notifies the new password request event.
     * @param account the account which requested a new password
     */
    void newPassword(final Account account);
    
    /**
     * Notifies the reset password event.
     * @param account the account which reseted its password
     */
    void resetPassword(final Account account);
}
