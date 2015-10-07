package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.cryptography.Sha512;
import com.daeliin.framework.commons.security.cryptography.Token;
import com.daeliin.framework.commons.security.details.UserDetails;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsEncryption {
    
    private final UserDetails userDetails; 
    private String password;
    private String token;
    
    public UserDetailsEncryption(final UserDetails userDetails) {
        this.userDetails = userDetails;
        
        generatePassword();
        generateToken();
    }
    
    private void generatePassword() {
        this.password = new BCryptPasswordEncoder().encode(this.userDetails.getClearPassword());
    }
    
    private void generateToken() {
        List<String> data = new LinkedList<>();
        data.add(userDetails.getEmail());
        
        this.token = new Token(data, new Sha512(), true).asString();
    }
    
    public String password() {
        return this.password;
    }
    
    public String token() {
        return this.token;
    }
}
