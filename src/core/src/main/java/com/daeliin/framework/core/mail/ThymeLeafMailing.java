package com.daeliin.framework.core.mail;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public abstract class ThymeLeafMailing {
    
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
    
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        
        emailTemplateResolver.setPrefix(path);
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        emailTemplateResolver.setOrder(1);
        
        return emailTemplateResolver;
    }
    
    @Bean
    @Autowired
    public SpringTemplateEngine templateEngine(ClassLoaderTemplateResolver emailTemplateResolver, ClassLoaderTemplateResolver webTemplateResolver) {
        
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        
        Set<TemplateResolver> templateResolvers = new HashSet<>();
        templateResolvers.add(emailTemplateResolver);
        
        templateEngine.setTemplateResolvers(templateResolvers);
        
        return templateEngine;
    }
}
