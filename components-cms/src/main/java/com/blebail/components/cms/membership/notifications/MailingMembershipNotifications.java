package com.blebail.components.cms.membership.notifications;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.core.mail.Mail;
import com.blebail.components.core.mail.Mailing;
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
    public void onSignUp(final Account account) {
        sendMail(account, "membership.mail.signup.subject", "signUp");
    }

    @Async
    @Override
    public void onActivate(final Account account) {
        sendMail(account, "membership.mail.activate.subject", "activate");
    }

    @Async
    @Override
    public void onNewPassword(final Account account) {
        sendMail(account, "membership.mail.newPassword.subject", "newPassword");
    }

    @Async
    @Override
    public void onResetPassword(final Account account) {
        sendMail(account, "membership.mail.resetPassword.subject", "resetPassword");
    }

    private void sendMail(final Account account, String subjectKey, String template) {
        Map<String, Object> parameters = addAccountParameters(account);

        try {
            Mail mail = Mail.builder()
                    .from(from)
                    .to(account.email)
                    .subject(messages.getMessage(subjectKey, null, Locale.getDefault()))
                    .templateName(template)
                    .parameters(parameters)
                    .build();

            mails.send(mail);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Mail could not be sent to account {}", account, e);
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
