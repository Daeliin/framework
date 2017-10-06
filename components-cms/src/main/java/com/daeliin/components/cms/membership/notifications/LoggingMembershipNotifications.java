package com.daeliin.components.cms.membership.notifications;

import com.daeliin.components.cms.credentials.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("!mail")
public class LoggingMembershipNotifications implements MembershipNotifications {

    @Override
    public void signUp(Account account) {
        log.info("The account " + account + " signed up");
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
