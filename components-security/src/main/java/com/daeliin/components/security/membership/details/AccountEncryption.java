package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.cryptography.Sha512;
import com.daeliin.components.security.cryptography.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Objects;

public class AccountEncryption {

    public final String password;
    public final String token;

    public AccountEncryption(String username, String clearPassword) {
        Objects.requireNonNull(username, "username should not be null");
        Objects.requireNonNull(clearPassword, "clearPassword should not be null");

        this.password = new BCryptPasswordEncoder().encode(clearPassword);
        this.token = new Token(Arrays.asList(username), new Sha512(), true).asString;
    }
}
