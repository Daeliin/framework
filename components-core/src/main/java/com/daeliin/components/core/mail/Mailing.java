package com.daeliin.components.core.mail;

import java.util.Date;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Processes and sends mails.
 */
@Slf4j
@Profile("mail")
@Component
public class Mailing {
    
    @Value("${daeliin.mail.domain.name}")
    private String domainName;
    
    @Value("${daeliin.mail.domain.url}")
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
            log.error(e.getMessage(), e);
        } 
    }
    
    private String processBody(Map<String, String> parameters, final String templateName) {
        Context context = new Context();
        
        addGlobalParameters(parameters);
        
        parameters.entrySet().forEach(parameter -> context.setVariable(parameter.getKey(), parameter.getValue()));
        
        return this.templateEngine.process(templateName, context);
    }
    
    private void addGlobalParameters(Map<String, String> parameters) {
        parameters.put("domainName", domainName);
        parameters.put("domainUrl", domainUrl);
    }
}
