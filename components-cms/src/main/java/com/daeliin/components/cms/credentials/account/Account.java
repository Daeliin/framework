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
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.enabled = enabled;
        this.password = Objects.requireNonNull(password);
        this.token = Objects.requireNonNull(token);
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
