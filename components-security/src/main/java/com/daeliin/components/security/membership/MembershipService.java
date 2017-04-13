package com.daeliin.components.security.membership;

import com.daeliin.components.core.event.EventLog;
import com.daeliin.components.core.event.EventLogService;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountService;
import com.daeliin.components.security.exception.AccountAlreadyExistException;
import com.daeliin.components.security.exception.InvalidTokenException;
import com.daeliin.components.security.membership.details.AccountDetailsService;
import com.daeliin.components.security.membership.notifications.MembershipNotifications;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void signUp(Account account) {
        if (accountDetailsService.exists(account)) {
            throw new AccountAlreadyExistException("The username already exist");
        }
        
        accountDetailsService.signUp(account);
        Account createdAccount = accountService.create(account);
        eventLogService.create(new EventLog("daeliin.membership.signup.event", LocalDateTime.now()));
        membershipNotifications.signUp(createdAccount);
        
        log.info(String.format("account[%s] signed up", createdAccount.getId()));
    }
    
    @Transactional
    public void activate(Long accountId, String activationToken) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("account[%s] not found", accountId));
        }
        
        try {
            Account account = accountService.findOne(accountId);
            accountDetailsService.activate(account, activationToken);
            accountService.update(accountId, account);
            eventLogService.create(new EventLog("daeliin.membership.activate.event", LocalDateTime.now()));
            membershipNotifications.activate(account);
            log.info(String.format("account[%s] activated", accountId));
        } catch(InvalidTokenException e) {
            log.warn(String.format("an attempt to activate account[%s] with an invalid token[%s] has been made", accountId, activationToken));
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public void newPassword(Long accountId) {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }
        
        eventLogService.create(new EventLog("daeliin.membership.newpassword.event", LocalDateTime.now()));
        membershipNotifications.newPassword(accountService.findOne(accountId));
                
        log.info(String.format("account[%s] requested a new password", accountId));
    }
    
    @Transactional
    public void resetPassword(Long accountId, String resetPasswordToken, String newPassword) throws InvalidTokenException {
        if (!accountService.exists(accountId)) {
            throw new PersistentResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }
        
        try {
            Account account = accountService.findOne(accountId);
            accountDetailsService.resetPassword(account, resetPasswordToken, newPassword);
            accountService.update(accountId, account);
            eventLogService.create(new EventLog("daeliin.membership.resetpassword.event", LocalDateTime.now()));
            membershipNotifications.resetPassword(account);
            log.info(String.format("account[%s] reseted its password", accountId));
        } catch (InvalidTokenException e) {
            log.warn(String.format("an attempt to reset account[%s] password with an invalid token[%s] has been made", accountId, resetPasswordToken));
            throw e;
        }
    }
}
