package com.daeliin.framework.security.membership;

import com.daeliin.framework.security.credentials.account.Account;
import com.daeliin.framework.security.cryptography.hash.Sha512;
import com.daeliin.framework.security.cryptography.hash.Token;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountEncryption {
    
    private final Account account; 
    private String password;
    private String token;
    
    public AccountEncryption(final Account account) {
        this.account = account;
        
        generatePassword();
        generateToken();
    }
    
    public String password() {
        return this.password;
    }
    
    public String token() {
        return this.token;
    }
    
    private void generatePassword() {
        this.password = new BCryptPasswordEncoder().encode(this.account.getClearPassword());
    }
    
    private void generateToken() {
        List<String> data = new LinkedList<>();
        data.add(account.getEmail());
        
        this.token = new Token(data, new Sha512(), true).asString();
    }
}
