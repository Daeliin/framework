package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.security.details.UserDetailsRepository;
import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.security.details.PersistentUserDetailsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
        service.create(userDetails);
    }
    
    @RequestMapping(value = "activate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void activate() {
    }
    
    @RequestMapping(value = "newpassword", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void newPassword() {
    }
    
    @RequestMapping(value = "resetpassword", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void resetPassword() {
    }
}
