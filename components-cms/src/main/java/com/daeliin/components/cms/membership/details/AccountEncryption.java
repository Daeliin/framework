package com.daeliin.components.cms.membership.details;

import com.daeliin.components.core.security.cryptography.Sha512;
import com.daeliin.components.core.security.cryptography.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Objects;

public class AccountEncryption {

    public final String password;
    public final String token;

    public AccountEncryption(String username, String clearPassword) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(clearPassword);

        this.password = new BCryptPasswordEncoder().encode(clearPassword);
        this.token = new Token(Arrays.asList(username), new Sha512(), true).asString;
    }
}
