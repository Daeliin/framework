package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.credentials.account.PersistentAccount;
import com.daeliin.framework.commons.security.credentials.account.PersistentAccountService;
import com.daeliin.framework.commons.security.exception.InvalidTokenException;
import com.daeliin.framework.commons.security.exception.AccountAlreadyExistException;
import com.daeliin.framework.commons.security.exception.WrongAccessException;
import com.daeliin.framework.commons.security.membership.MembershipNotifications;
import com.daeliin.framework.commons.security.membership.ResetPasswordRequest;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import com.daeliin.framework.security.credentials.details.AccountDetailsService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(API_ROOT_PATH + "/public/membership")
public class MembershipController {
    
    protected final PersistentAccountService accountService;
    protected final AccountDetailsService accountDetailsService;
    protected final MembershipNotifications membershipNotifications;

    @Autowired
    public MembershipController(
        final PersistentAccountService accountService, 
        final AccountDetailsService accountDetailsService, 
        final MembershipNotifications membershipNotifications) {
        
        this.accountService = accountService;
        this.accountDetailsService = accountDetailsService;
        this.membershipNotifications = membershipNotifications;
    }
    
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void signUp(@RequestBody @Valid PersistentAccount account) {
        if (accountDetailsService.exists(account)) {
            throw new AccountAlreadyExistException("The username already exist");
        }
        
        accountDetailsService.signUp(account);
        PersistentAccount createdAccount = accountService.create(account);
        membershipNotifications.signUp(createdAccount);
        
        log.info(String.format("account[%s] signed up", createdAccount.getId()));
    }
    
    @RequestMapping(value = "activate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void activate(
        @RequestParam(value = "id") Long accountId,
        @RequestParam(value = "token") String activationToken) {
        
        if (!accountService.exists(accountId)) {
            throw new ResourceNotFoundException(String.format("account[%s] not found", accountId));
        }
        
        try {
            PersistentAccount account = accountService.findOne(accountId);
            accountDetailsService.activate(account, activationToken);
            accountService.update(accountId, account);
            membershipNotifications.activate(account);
            log.info(String.format("account[%s] activated", accountId));
        } catch(InvalidTokenException e) {
            log.warn(String.format("an attempt to activate account[%s] with an invalid token[%s] has been made", accountId, activationToken));
            throw new WrongAccessException("Actication token is not valid");
        }
    }
    
    @RequestMapping(value = "newpassword", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void newPassword(@RequestParam(value = "id") Long accountId) {
        if (!accountService.exists(accountId)) {
            throw new ResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }
        
        membershipNotifications.newPassword(accountService.findOne(accountId));
                
        log.info(String.format("account[%s] requested a new password", accountId));
    }
    
    @RequestMapping(value = "resetpassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        Long accountId = resetPasswordRequest.getAccountId();
        String resetPasswordToken = resetPasswordRequest.getToken();
        String newPassword = resetPasswordRequest.getNewPassword();
        
        if (!accountService.exists(accountId)) {
            throw new ResourceNotFoundException(String.format("Account[%s] not found", accountId));
        }
        
        try {
            PersistentAccount account = accountService.findOne(accountId);
            accountDetailsService.resetPassword(account, resetPasswordToken, newPassword);
            accountService.update(accountId, account);
            membershipNotifications.resetPassword(account);
            log.info(String.format("account[%s] reseted its password", accountId));
        } catch (InvalidTokenException e) {
            log.warn(String.format("an attempt to reset account[%s] password with an invalid token[%s] has been made", accountId, resetPasswordToken));
            throw new WrongAccessException("Reset password token is not valid");
        }
    }
}
