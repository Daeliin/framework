package com.daeliin.framework.core.mail;

import com.daeliin.framework.core.exception.MailBuildingException;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString(of = {"toString"})
public class Email {
    
    private final String toString;
    
    public Email(final String email) throws MailBuildingException {
        if (StringUtils.isBlank(email)) {
            throw new MailBuildingException("Mail from should not be empty");
        } else if (!email.matches(".+@.+")) {
            throw new MailBuildingException("Mail from should be valid email");
        }
        
        this.toString = email;
    }
}
