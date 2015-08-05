package com.daeliin.framework.sample;

import com.daeliin.framework.security.oauth2.AuthenticationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends AuthenticationServer {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
