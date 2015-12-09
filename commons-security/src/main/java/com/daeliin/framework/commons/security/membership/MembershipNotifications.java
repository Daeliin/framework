package com.daeliin.framework.commons.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;

public interface MembershipNotifications<E extends UserDetails> {
    
    /**
     * Notifiy the sign up event.
     * @param userDetails the user details which signed up
     */
    void signUp(final E userDetails);
    
    /**
     * Notifiy the activation event.
     * @param userDetails the user details which activated
     */
    void activate(final E userDetails);
    
    /**
     * Notifiy the new password request event.
     * @param userDetails the user details which requested a new password
     */
    void newPassword(final E userDetails);
    
    /**
     * Notifiy the reset password event.
     * @param userDetails the user details which reseted its password
     */
    void resetPassword(final E userDetails);
}
