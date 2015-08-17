package com.daeliin.framework.security.authentication.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Value("${authentication.session.timeout}")
    private int sessionTimeout;
    
    private final RequestCache requestCache = new HttpSessionRequestCache();
    
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication) {
        
        if (request.getSession(false) != null) {
            request.getSession(false).setMaxInactiveInterval(sessionTimeout);
        }
        
        String targetUrlParameter = getTargetUrlParameter();
        
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            return;
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        
        clearAuthenticationAttributes(request);
    }
}
