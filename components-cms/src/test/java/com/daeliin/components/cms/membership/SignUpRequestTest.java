package com.daeliin.components.cms.membership;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class SignUpRequestTest {

    @Test
    public void shouldThrowException_whenUsernameIsNull() {
        assertThrows(Exception.class, () ->  new SignUpRequest(null, "john@daeliin.com", "password"));
    }

    @Test
    public void shouldAssignAnUsername() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").username).isEqualTo("john");
    }

    @Test
    public void shouldThrowException_whenEmailIsNull() {
        assertThrows(Exception.class, () -> new SignUpRequest("john", null, "password"));
    }

    @Test
    public void shouldAssignAnEmail() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").email).isEqualTo("john@daeliin.com");
    }

    @Test
    public void shouldThrowException_whenClearPasswordIsNull() {
        assertThrows(Exception.class, () -> new SignUpRequest("john", "john@daeliin.com", null));
    }

    @Test
    public void shouldAssignAClearPassword() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").clearPassword).isEqualTo("password");
    }

    @Test
    public void shouldPrintsItsUsernameAndEmail() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").toString()).contains("john", "john@daeliin.com");
    }

}