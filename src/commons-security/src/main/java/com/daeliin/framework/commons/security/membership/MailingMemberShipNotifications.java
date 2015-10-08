package com.daeliin.framework.commons.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.core.exception.MailBuildingException;
import com.daeliin.framework.core.mail.Mail;
import com.daeliin.framework.core.mail.Mails;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class MailingMemberShipNotifications<E extends UserDetails> implements MembershipNotifications<E> {

    @Value("${mail.from}")
    private String from;
    
    protected Mails mails;

    @Autowired
    public MailingMemberShipNotifications(final Mails mails) {
        this.mails = mails;
    }
    
    @Override
    public void signUp(final E userDetails) {
        Map<String, String> parameters = addUserDetailsParameters(userDetails);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(userDetails.getEmail())
                .subject("Welcome, activate your account")
                .templateName("signUp.html")
                .parameters(parameters)
                .build();
            
                mails.send(mail);
        } catch (MailBuildingException e) {
            log.error("Sign up mail for user[" + userDetails.getId() + "] was invalid", e);
        }
    }

    @Override
    public void activate(final E userDetails) {
        Map<String, String> parameters = addUserDetailsParameters(userDetails);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(userDetails.getEmail())
                .subject("Thanks for activating your account")
                .templateName("activate.html")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error("Activate mail for user[" + userDetails.getId() + "] was invalid", e);
        }
    }

    @Override
    public void newPassword(final E userDetails) {
        Map<String, String> parameters = addUserDetailsParameters(userDetails);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(userDetails.getEmail())
                .subject("You requested a  new password")
                .templateName("newPassword.html")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error("New password mail for user[" + userDetails.getId() + "] was invalid", e);
        }
    }

    @Override
    public void resetPassword(final E userDetails) {
        Map<String, String> parameters = addUserDetailsParameters(userDetails);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(userDetails.getEmail())
                .subject("Your password has been changed")
                .templateName("resetPassword.html")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error("Reset password mail for user[" + userDetails.getId() + "] was invalid", e);
        }
    }
    
    private Map<String, String> addUserDetailsParameters(final E userDetails) {
        Map<String, String> userDetailsParameters = new HashMap<>();
        
        userDetailsParameters.put("userName", userDetails.getUsername());
        userDetailsParameters.put("userEmail", userDetails.getEmail());
        userDetailsParameters.put("userToken", userDetails.getToken());
            
        return userDetailsParameters;
    }
}
