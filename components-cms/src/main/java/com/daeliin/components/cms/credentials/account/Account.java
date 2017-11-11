package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.persistence.resource.PersistentResource;

import java.time.Instant;
import java.util.Objects;

public class Account extends PersistentResource<String> implements Comparable<Account> {

    public final String username;
    public final String email;
    public final boolean enabled;
    public final String password;
    public final String token;

    public Account(String id, Instant creationDate, String username, String email, boolean enabled, String password, String token) {
        super(id, creationDate);
        this.username = Objects.requireNonNull(username, "username should not be null");
        this.email = Objects.requireNonNull(email, "email should not be null");
        this.enabled = enabled;
        this.password = Objects.requireNonNull(password, "password should not be null");
        this.token = Objects.requireNonNull(token, "token should not be null");
    }

    @Override
    public String toString() {
        return super.toStringHelper()
                .add("email", email)
                .add("username", username)
                .add("enabled", enabled)
                .toString();
    }

    @Override
    public int compareTo(Account other) {
        if (this.equals(other)) {
            return 0;
        }

        return this.username.compareTo(other.username);
    }
}
