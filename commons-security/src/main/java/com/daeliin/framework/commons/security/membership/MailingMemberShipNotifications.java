package com.daeliin.framework.commons.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.core.mail.Mail;
import com.daeliin.framework.core.mail.Mails;
import com.daeliin.framework.core.resource.exception.MailBuildingException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class MailingMemberShipNotifications<E extends UserDetails> implements MembershipNotifications<E> {

    @Value("${mail.from}")
    private String from;
    
    @Autowired
    protected Mails mails;

    @Override
    public void signUp(final E userDetails) {
        Map<String, String> parameters = addUserDetailsParameters(userDetails);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(userDetails.getEmail())
                .subject("Welcome, activate your account")
                .templateName("signUp")
                .parameters(parameters)
                .build();
                
                mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Sign up mail for user %s was invalid", userDetails), e);
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
                .templateName("activate")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Activate mail for user %s was invalid", userDetails), e);
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
                .templateName("newPassword")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("New password mail for user %s was invalid", userDetails), e);
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
                .templateName("resetPassword")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Reset password mail for user %s was invalid", userDetails), e);
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
