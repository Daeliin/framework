package com.blebail.components.cms.membership.notifications;

import com.blebail.components.cms.credentials.account.Account;


public interface MembershipNotifications {
    
    /**
     * Notifies the sign up event.
     * @param account the account which signed up
     */
    void onSignUp(final Account account);
    
    /**
     * Notifies the activation event.
     * @param account the account which activated
     */
    void onActivate(final Account account);
    
    /**
     * Notifies the new password request event.
     * @param account the account which requested a new password
     */
    void onNewPassword(final Account account);
    
    /**
     * Notifies the reset password event.
     * @param account the account which reseted its password
     */
    void onResetPassword(final Account account);
}
