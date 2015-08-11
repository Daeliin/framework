package com.daeliin.framework.sample;

import com.daeliin.framework.security.authentication.form.FormAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.daeliin.framework")
@EntityScan(basePackages = "com.daeliin.framework")
@EnableJpaRepositories(basePackages = "com.daeliin.framework")
@PropertySource("security.properties")
public class Application  {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Configuration
    protected static class Authentication extends FormAuthentication {
    }
}
