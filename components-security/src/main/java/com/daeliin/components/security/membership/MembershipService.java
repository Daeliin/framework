package com.daeliin.components.security.membership;

import com.daeliin.components.persistence.event.EventLogService;
import com.daeliin.components.persistence.exception.PersistentResourceNotFoundException;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.daeliin.components.security.exception.AccountAlreadyExistException;
import com.daeliin.components.security.exception.InvalidTokenException;
import com.daeliin.components.security.membership.details.AccountDetailsService;
import com.daeliin.components.security.membership.notifications.MembershipNotifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Slf4j
@Service
public class MembershipService {

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
            throw new AccountAlreadyExistException("The username already exist");
        }

        Account account = accountDetailsService.signUp(signUpRequest);
        eventLogService.create(String.format("%s signed up", account.username));
        membershipNotifications.signUp(account);

        log.info(String.format("%s signed up", account));

        return account;
    }

    @Transactional
    public void activate(String accountId, String activationToken) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("account %s not found", accountId));
        }

        Account account = accountService.findOne(accountId);

        try {
            account = accountDetailsService.activate(account, activationToken);
            eventLogService.create(String.format("%s activated its account", account.username));
            membershipNotifications.activate(account);
            log.info(String.format("%s activated", account));
        } catch(InvalidTokenException e) {
            log.warn(String.format("an attempt to activate %s with an invalid token[%s] has been made", account, activationToken));
            throw e;
        }
    }

    @Transactional
    public void newPassword(String accountId) {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }

        Account account = accountService.findOne(accountId);
        eventLogService.create(String.format("%s requested a new password ", account.username));
        membershipNotifications.newPassword(account);

        log.info(String.format("%s requested a new password", account));
    }

    @Transactional
    public void resetPassword(String accountId, String resetPasswordToken, String newPassword) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }

        Account account = accountService.findOne(accountId);

        try {
            account = accountDetailsService.resetPassword(account, resetPasswordToken, newPassword);
            eventLogService.create(String.format("%s reseted its password ", account.username));
            membershipNotifications.resetPassword(account);
            log.info(String.format("%s reseted its password", account));
        } catch (InvalidTokenException e) {
            log.warn(String.format("an attempt to reset %s password with an invalid token[%s] has been made", account, resetPasswordToken));
            throw e;
        }
    }
}
