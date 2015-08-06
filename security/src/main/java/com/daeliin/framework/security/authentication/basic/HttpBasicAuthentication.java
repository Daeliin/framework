package com.daeliin.framework.security.authentication.basic;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@ComponentScan
public class HttpBasicAuthentication extends WebSecurityConfigurerAdapter {
    
    private static final String USERS_BY_USERNAME_QUERY = 
        "SELECT username, password, enabled " + 
        "FROM user " + 
        "WHERE username = LOWER(?)";
    
    private static final String AUTHORITIES_BY_USERNAME_QUERY = 
        "SELECT user.username, credential.label AS authorities " +
        "FROM user, credential, user_credential" +
        "WHERE user.username = ? AND user.id = user_credential.user_id AND credential.id = user_credential.credential_id";
    
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    
    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
    
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(new DaoAuthenticationProvider())
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
            .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY)
            .passwordEncoder(passwordEncoder());        
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
                .antMatchers(
                    "/api/**")
                        .authenticated()
                .antMatchers(
                    "/",
                    "/stylesheets/**",
                    "/images/**",
                    "/scripts/**")
                        .permitAll()
        .and()
            .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
        .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
    }
}
