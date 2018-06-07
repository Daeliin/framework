package com.daeliin.components.webservices.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

    public static boolean isFrontOfficeRequest() {
        return !isBackOfficeRequest();
    }

    public static boolean isBackOfficeRequest() {
        HttpServletRequest currentRequest = getCurrentRequest();

        if (currentRequest == null) {
            throw new IllegalStateException("No current request found, currently not in a web environment");
        }

        return StringUtils.isNotBlank(currentRequest.getHeader("Authorization"));
    }

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
