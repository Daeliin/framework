package com.daeliin.framework.security.mock.membership;

import com.daeliin.framework.commons.security.membership.MembershipNotifications;
import com.daeliin.framework.security.mock.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleMemberShipNotifications implements MembershipNotifications<User> {

    @Override
    public void signUp(User userDetails) {
        log.info("User signed up");
    }

    @Override
    public void activate(User userDetails) {
        log.info("User activated");
    }

    @Override
    public void newPassword(User userDetails) {
        log.info("User requested a new password");
    }

    @Override
    public void resetPassword(User userDetails) {
        log.info("User did reset his password");
    }
}
