package com.daeliin.framework.core.mock.configuration;

import com.daeliin.framework.core.mail.ThymeLeafMailing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mail.properties")
public class Mailing extends ThymeLeafMailing {
}
