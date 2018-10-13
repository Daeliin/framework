package com.blebail.components.cms.mail;

import com.blebail.components.core.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;

/**
 * Processes and sends mails.
 */
@Profile("mail")
@Component
public class Mailing {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Mailing.class);
    
    @Value("${blebail.mail.domain.name}")
    private String domainName;
    
    @Value("${blebail.mail.domain.url}")
    private String domainUrl;
    
    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl mailSender;

    @Inject
    public Mailing(final TemplateEngine templateEngine, final JavaMailSenderImpl mailSender) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
    }
    
    public void send(Mail mail) {
        MimeMessage message = this.mailSender.createMimeMessage();
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mail.to());
            helper.setSentDate(new Date());
            helper.setFrom(mail.from());
            helper.setSubject(mail.subject());
            helper.setText(processBody(mail.parameters(), mail.templateName()), true);
            
            this.mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        } 
    }
    
    private String processBody(Map<String, Object> parameters, final String templateName) {
        Context context = new Context();
        
        addGlobalParameters(parameters);
        parameters.forEach(context::setVariable);
        
        return this.templateEngine.process(templateName, context);
    }
    
    private void addGlobalParameters(Map<String, Object> parameters) {
        parameters.put("domainName", domainName);
        parameters.put("domainUrl", domainUrl);
    }
}
