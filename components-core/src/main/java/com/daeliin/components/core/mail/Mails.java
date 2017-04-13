package com.daeliin.components.core.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Profile("mail")
@Configuration
public class Mails {
    
    @Value("${daeliin.mail.host}")
    private String host;
    
    @Value("${daeliin.mail.port}")
    private int port;
    
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(host);
        mailSender.setPort(port);
        
        return mailSender;
    }
}
