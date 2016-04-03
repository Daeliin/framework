package com.daeliin.framework.commons.security.credentials.account;

import com.daeliin.framework.commons.model.PersistentResource;
import java.util.Date;

public interface Account extends PersistentResource<Long> {

    String getEmail();
    
    void setEmail(final String email);
    
    String getUsername();
    
    void setUsername(final String username);
    
    String getClearPassword();
    
    void setClearPassword(final String clearPassword);
    
    String getPassword();
    
    void setPassword(final String password);
    
    boolean isEnabled();
    
    void setEnabled(final boolean enabled);
    
    String getToken();
    
    void setToken(final String token);
    
    Date getSignUpDate();
    
    void setSignUpDate(final Date signUpDate);
}
