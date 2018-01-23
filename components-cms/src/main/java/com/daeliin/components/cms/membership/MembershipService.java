package com.daeliin.components.cms.membership;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.event.EventLogService;
import com.daeliin.components.cms.exception.InvalidTokenException;
import com.daeliin.components.cms.membership.details.AccountDetailsService;
import com.daeliin.components.cms.membership.notifications.MembershipNotifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.NoSuchElementException;

@Service
public class MembershipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipService.class);
    
    protected final AccountService accountService;
    protected final AccountDetailsService accountDetailsService;
    protected final EventLogService eventLogService;
    protected final MembershipNotifications membershipNotifications;

    @Inject
    public MembershipService(
        final AccountService accountService,
        final AccountDetailsService accountDetailsService,
        final EventLogService eventLogService,
        final MembershipNotifications membershipNotifications) {

        this.accountService = accountService;
        this.accountDetailsService = accountDetailsService;
        this.eventLogService = eventLogService;
        this.membershipNotifications = membershipNotifications;
    }

    @Transactional
    public Account signUp(SignUpRequest signUpRequest) {
        if (accountService.usernameExists(signUpRequest.username)) {
            throw new IllegalStateException("The username already exist");
        }

        Account account = accountDetailsService.signUp(signUpRequest);
        eventLogService.create(String.format("%s signed up", account.username));
        membershipNotifications.signUp(account);

        LOGGER.info(String.format("%s signed up", account));

        return account;
    }

    @Transactional
    public void activate(String accountId, String activationToken) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new NoSuchElementException(String.format("account %s not found", accountId));
        }

        Account account = accountService.findOne(accountId);

        try {
            account = accountDetailsService.activate(account, activationToken);
            eventLogService.create(String.format("%s activated its account", account.username));
            membershipNotifications.activate(account);
            LOGGER.info(String.format("%s activated", account));
        } catch(InvalidTokenException e) {
            LOGGER.warn(String.format("an attempt to activate %s with an invalid token[%s] has been made", account, activationToken));
            throw e;
        }
    }

    @Transactional
    public void newPassword(String accountId) {
        if (!accountService.exists(accountId)) {
            throw new NoSuchElementException(String.format("Account[%s] not found", accountId));
        }

        Account account = accountService.findOne(accountId);
        eventLogService.create(String.format("%s requested a new password ", account.username));
        membershipNotifications.newPassword(account);

        LOGGER.info(String.format("%s requested a new password", account));
    }

    @Transactional
    public void resetPassword(String accountId, String resetPasswordToken, String newPassword) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new NoSuchElementException(String.format("Account[%s] not found", accountId));
        }

        Account account = accountService.findOne(accountId);

        try {
            account = accountDetailsService.resetPassword(account, resetPasswordToken, newPassword);
            eventLogService.create(String.format("%s reseted its password ", account.username));
            membershipNotifications.resetPassword(account);
            LOGGER.info(String.format("%s reseted its password", account));
        } catch (InvalidTokenException e) {
            LOGGER.warn(String.format("an attempt to reset %s password with an invalid token[%s] has been made", account, resetPasswordToken));
            throw e;
        }
    }
}
