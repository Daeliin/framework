package com.daeliin.components.cms.membership;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class SignUpRequest {

    public final String username;
    public final String email;
    public final String clearPassword;

    public SignUpRequest(String username, String email, String clearPassword) {
        this.username = Objects.requireNonNull(username, "username should not be null");
        this.email = Objects.requireNonNull(email, "email should not be null");
        this.clearPassword = Objects.requireNonNull(clearPassword, "password should not be null");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("email", email)
                .toString();
    }
}
