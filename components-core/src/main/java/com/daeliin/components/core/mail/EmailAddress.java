package com.daeliin.components.core.mail;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class EmailAddress {
    
    public final String value;
    
    public EmailAddress(final String emailAddress) {
        if (StringUtils.isBlank(emailAddress)) {
            throw new IllegalArgumentException("Email address is blank");
        } else if (!emailAddress.matches(".+@.+")) {
            throw new IllegalArgumentException("Email address format is not valid");
        }
        
        this.value = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("value", value)
            .toString();
    }
}
