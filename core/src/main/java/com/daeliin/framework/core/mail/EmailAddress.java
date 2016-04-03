package com.daeliin.framework.core.mail;

import com.daeliin.framework.core.resource.exception.MailBuildingException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@ToString(of = {"toString"})
@EqualsAndHashCode(of = {"toString"})
public class EmailAddress {
    
    private final String toString;
    
    public EmailAddress(final String emailAddress) throws MailBuildingException {
        if (StringUtils.isBlank(emailAddress)) {
            throw new MailBuildingException("Email address is blank");
        } else if (!emailAddress.matches(".+@.+")) {
            throw new MailBuildingException("Email address format is not valid");
        }
        
        this.toString = emailAddress;
    }
}
