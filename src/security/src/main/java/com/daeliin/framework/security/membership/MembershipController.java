package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.security.details.PersistentUserDetailsService;
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
public abstract class MembershipController<E extends UserDetails, S extends ResourceService<UserDetails, Long, UserDetailsRepository<UserDetails, Long>>> {
    
    final protected S service;
    final protected PersistentUserDetailsService userDetailsService;
    
    @Autowired
    protected MembershipController(final PersistentUserDetailsService userDetailsService, final S service) {
        this.userDetailsService = userDetailsService;
        this.service = service;
    }
    
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void signUp(@RequestBody @Valid E userDetails) {
        userDetailsService.generate(userDetails);
        UserDetails createdUserDetails = service.create(userDetails);
        
        log.info("user[" + createdUserDetails.getId() + "] signed up");
    }
    
    @RequestMapping(value = "activate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void activate(
        @RequestParam(value = "id") Long userDetailsId,
        @RequestParam(value = "token") String activationToken) {
        // Activate, generate new token, send mail to user 
        log.info("user [" + userDetailsId + "] activated");
    }
    
    @RequestMapping(value = "newpassword", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void newPassword(@RequestParam(value = "id") Long userDetailsId) {
        // send reset password mail to user
        log.info("user [" + userDetailsId + "] requested a new password");
    }
    
    @RequestMapping(value = "resetpassword", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void resetPassword(
            @RequestParam(value = "id") Long userDetailsId,
            @RequestParam(value = "token") String newPasswordToken) {
        // Check that the connected user id is the same as the userDetailsId
        // reset password, send password changed to user, generate new token
        log.info("user [" + userDetailsId + "] reseted its password");
    }
}
