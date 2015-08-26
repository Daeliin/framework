package com.daeliin.framework.security.authentication.form;

import com.daeliin.framework.security.details.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public abstract class FormAuthentication extends WebSecurityConfigurerAdapter {
    
    @Value("${api.root.path}")
    private String apiRootPath;
    
    @Value("${authentication.endpoint}")
    private String authenticationEndpoint;
    
    @Value("${authentication.logout.endpoint}")
    private String authenticationLogoutEndpoint;
    
    @Value("${authentication.username.parameter}")
    private String authenticationUsernameParameter;
    
    @Value("${authentication.password.parameter}")
    private String authenticationPasswordParameter;
    
    private static final String SPRING_SECURITY_REMEMBER_ME_COOKIE = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
    
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;
    
    @Autowired
    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Autowired
    private RestLogoutSuccessHandler logoutSuccessHandler;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
    
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
            .authorizeRequests()
                .antMatchers(
                    apiRootPath + authenticationEndpoint)
                    .permitAll()
                .antMatchers(
                    apiRootPath + "/**")
                    .authenticated()
                .and()
            .formLogin()
                .usernameParameter(authenticationUsernameParameter)
                .passwordParameter(authenticationPasswordParameter)
                .loginProcessingUrl(apiRootPath + authenticationEndpoint)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
            .logout()
                .logoutUrl(apiRootPath + authenticationLogoutEndpoint)
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(SPRING_SECURITY_REMEMBER_ME_COOKIE)
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
            .addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
    }
}
