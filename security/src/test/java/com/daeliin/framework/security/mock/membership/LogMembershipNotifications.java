package com.daeliin.framework.security.mock.membership;

import com.daeliin.framework.commons.security.membership.MembershipNotifications;
import com.daeliin.framework.security.mock.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogMembershipNotifications implements MembershipNotifications<User> {

    @Override
    public void signUp(User userDetails) {
        log.info("The user " + userDetails + " signeUp");
    }

    @Override
    public void activate(User userDetails) {
        log.info("The user " + userDetails + " activated");
    }

    @Override
    public void newPassword(User userDetails) {
        log.info("The user " + userDetails + " requested a new password");
    }

    @Override
    public void resetPassword(User userDetails) {
        log.info("The user " + userDetails + " reseted its password");
    }
}
