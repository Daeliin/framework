package com.daeliin.framework.security.mock.membership;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.membership.MembershipNotifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogMembershipNotifications implements MembershipNotifications {

    @Override
    public void signUp(Account account) {
        log.info("The account " + account + " signeUp");
    }

    @Override
    public void activate(Account account) {
        log.info("The account " + account + " activated");
    }

    @Override
    public void newPassword(Account account) {
        log.info("The account " + account + " requested a new password");
    }

    @Override
    public void resetPassword(Account account) {
        log.info("The account " + account + " reseted its password");
    }
}
