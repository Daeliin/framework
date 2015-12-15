package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.membership.ResetPasswordRequest;
import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import com.daeliin.framework.commons.security.exception.InvalidTokenException;
import com.daeliin.framework.commons.security.exception.UserDetailsAlreadyExistException;
import com.daeliin.framework.commons.security.exception.WrongAccessException;
import com.daeliin.framework.commons.security.membership.MembershipNotifications;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import com.daeliin.framework.core.resource.service.ResourceService;
import com.daeliin.framework.security.details.PersistentUserDetailsService;
import java.io.Serializable;
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
public abstract class MembershipController<E extends UserDetails, ID extends Serializable, R extends UserDetailsRepository<E, ID>, S extends ResourceService<E, ID, R>> {
    
    @Autowired
    protected S service;
    
    @Autowired
    protected PersistentUserDetailsService userDetailsService;
    
    @Autowired
    protected MembershipNotifications membershipNotifications;
    
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void signUp(@RequestBody @Valid E userDetails) {
        if (userDetailsService.exists(userDetails)) {
            throw new UserDetailsAlreadyExistException("The username already exist");
        }
        
        userDetailsService.signUp(userDetails);
        UserDetails createdUserDetails = service.create(userDetails);
        membershipNotifications.signUp(createdUserDetails);
        
        log.info("user[" + createdUserDetails.getId() + "] signed up");
    }
    
    @RequestMapping(value = "activate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void activate(
        @RequestParam(value = "id") ID userDetailsId,
        @RequestParam(value = "token") String activationToken) {
        if (!service.exists(userDetailsId)) {
            throw new ResourceNotFoundException("user[" + userDetailsId + "] not found");
        }
        
        try {
            UserDetails userDetails = service.findOne(userDetailsId);
            userDetailsService.activate(userDetails, activationToken);
            membershipNotifications.activate(userDetails);
            log.info("user[" + userDetailsId + "] activated");
        } catch(InvalidTokenException e) {
            log.warn("an attempt to activate user[" + userDetailsId + "] with an invalid token[" + activationToken + " has been made");
            throw new WrongAccessException("Actication token is not valid");
        }
    }
    
    @RequestMapping(value = "newpassword", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void newPassword(@RequestParam(value = "id") ID userDetailsId) {
        if (!service.exists(userDetailsId)) {
            throw new ResourceNotFoundException("User[" + userDetailsId + "] not found");
        }
        
        membershipNotifications.newPassword(service.findOne(userDetailsId));
                
        log.info("user[" + userDetailsId + "] requested a new password");
    }
    
    @RequestMapping(value = "resetpassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest<ID> resetPasswordRequest) {
        ID userDetailsId = resetPasswordRequest.getUserDetailsId();
        String resetPasswordToken = resetPasswordRequest.getToken();
        String newPassword = resetPasswordRequest.getNewPassword();
        
        if (!service.exists(userDetailsId)) {
            throw new ResourceNotFoundException("User[" + userDetailsId + "] not found");
        }
        
        try {
            UserDetails userDetails = service.findOne(userDetailsId);
            userDetailsService.resetPassword(userDetails, resetPasswordToken, newPassword);
            membershipNotifications.resetPassword(userDetails);
            log.info("user[" + userDetailsId + "] reseted its password");
        } catch (InvalidTokenException e) {
            log.warn("an attempt to reset user[" + userDetailsId + "] password with an invalid token[" + resetPasswordToken + " has been made");
            throw new WrongAccessException("Reset password token is not valid");
        }
    }
}
