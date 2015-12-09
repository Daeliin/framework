package com.daeliin.framework.security.authentication.form;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException exception) throws IOException {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication failure");
    }
}
