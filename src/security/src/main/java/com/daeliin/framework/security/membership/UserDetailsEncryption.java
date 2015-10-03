package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.details.UserDetails;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.userDetails.getPassword());
    }
    
    private void generateToken() {
        StringBuilder sb = new StringBuilder();
        String randomData = new Date().toString();
        
        sb
            .append(this.userDetails.getUsername())
            .append(randomData);
        
        this.token = DigestUtils.sha512Hex(sb.toString());
    }
    
    public String password() {
        return this.password;
    }
    
    public String token() {
        return this.token;
    }
}
