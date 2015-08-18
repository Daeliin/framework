package com.daeliin.framework.sample.configuration;

import com.daeliin.framework.security.authentication.form.FormAuthentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:security.properties")
public class Authentication extends FormAuthentication {
}
