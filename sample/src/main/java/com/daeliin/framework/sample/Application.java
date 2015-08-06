package com.daeliin.framework.sample;

import com.daeliin.framework.security.authentication.basic.HttpBasicAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends HttpBasicAuthentication {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
