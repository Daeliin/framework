package com.blebail.components.cms.membership.notifications;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.core.mail.Mailing;
import com.blebail.components.core.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@Profile("mail")
public class MailingMembershipNotifications implements MembershipNotifications {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingMembershipNotifications.class);
    
    @Value("${blebail.mail.from}")
    private String from;
    
    @Inject
    protected Mailing mails;

    @Inject
    protected MessageSource messages;
    
    @Async
    @Override
    public void signUp(final Account account) {
        Map<String, Object> parameters = addAccountParameters(account);
        
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
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("Sign up mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void activate(final Account account) {
        Map<String, Object> parameters = addAccountParameters(account);
        
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
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("Activate mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void newPassword(final Account account) {
        Map<String, Object> parameters = addAccountParameters(account);
        
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
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("New password mail for account %s was invalid", account), e);
        }
    }

    @Async
    @Override
    public void resetPassword(final Account account) {
        Map<String, Object> parameters = addAccountParameters(account);
        
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
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("Reset password mail for account %s was invalid", account), e);
        }
    }
    
    private Map<String, Object> addAccountParameters(final Account account) {
        Map<String, Object> accountParameters = new HashMap<>();
        
        accountParameters.put("userId", account.id());
        accountParameters.put("userName", account.username);
        accountParameters.put("userEmail", account.email);
        accountParameters.put("userToken", account.token);
            
        return accountParameters;
    }
}
