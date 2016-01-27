package com.daeliin.framework.core.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Profile("mail")
@Configuration
public class ThymeLeafMailing {
    
    @Value("${mail.host}")
    private String host;
    
    @Value("${mail.port}")
    private int port;
    
    @Value("${mail.path}")
    private String path;
    
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(host);
        mailSender.setPort(port);
        
        return mailSender;
    }
}
