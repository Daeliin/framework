package com.daeliin.framework.core.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.daeliin")
//@EntityScan(basePackages = "com.daeliin")
//@EnableJpaRepositories(basePackages = "com.daeliin")
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
