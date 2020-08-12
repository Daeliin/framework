package com.blebail.components.cms.membership;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class SignUpRequest {

    public final String username;
    public final String email;
    public final String clearPassword;

    public SignUpRequest(String username, String email, String clearPassword) {
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.clearPassword = Objects.requireNonNull(clearPassword);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("email", email)
                .toString();
    }
}
