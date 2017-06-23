package com.daeliin.components.security.membership.notifications;

import com.daeliin.components.core.mail.Mail;
import com.daeliin.components.core.mail.MailBuildingException;
import com.daeliin.components.core.mail.Mailing;
import com.daeliin.components.security.credentials.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@Profile("mail")
public class MailingMembershipNotifications implements MembershipNotifications {

    @Value("${daeliin.mail.from}")
    private String from;
    
    @Inject
    protected Mailing mails;

    @Inject
    protected MessageSource messages;
    
    @Async
    @Override
    public void signUp(final Account account) {
        Map<String, String> parameters = addAccountParameters(account);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(account.email)
                .subject(messages.getMessage("membership.mail.signup.subject", null, Locale.getDefault()))
                .templateName("signUp")
                .parameters(parameters)
                .build();
                
                mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Sign up mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void activate(final Account account) {
        Map<String, String> parameters = addAccountParameters(account);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(account.email)
                .subject(messages.getMessage("membership.mail.activate.subject", null, Locale.getDefault()))
                .templateName("activate")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Activate mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void newPassword(final Account account) {
        Map<String, String> parameters = addAccountParameters(account);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(account.email)
                .subject(messages.getMessage("membership.mail.newPassword.subject", null, Locale.getDefault()))
                .templateName("newPassword")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("New password mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void resetPassword(final Account account) {
        Map<String, String> parameters = addAccountParameters(account);
        
        try {
            Mail mail = 
                Mail.builder()
                .from(from)
                .to(account.email)
                .subject(messages.getMessage("membership.mail.resetPassword.subject", null, Locale.getDefault()))
                .templateName("resetPassword")
                .parameters(parameters)
                .build();
            
            mails.send(mail);
        } catch (MailBuildingException e) {
            log.error(String.format("Reset password mail for account %s was invalid", account), e);
        }
    }
    
    private Map<String, String> addAccountParameters(final Account account) {
        Map<String, String> accountParameters = new HashMap<>();
        
        accountParameters.put("userid", account.getId());
        accountParameters.put("userName", account.username);
        accountParameters.put("userEmail", account.email);
        accountParameters.put("userToken", account.token);
            
        return accountParameters;
    }
}
