package com.daeliin.components.cms.membership;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class SignUpRequestTest {

    @Test(expected = Exception.class)
    public void shouldThrowException_whenUsernameIsNull() {
        new SignUpRequest(null, "john@daeliin.com", "password");
    }

    @Test
    public void shouldAssignAnUsername() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").username).isEqualTo("john");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenEmailIsNull() {
        new SignUpRequest("john", null, "password");
    }

    @Test
    public void shouldAssignAnEmail() {
        assertThat(new SignUpRequest("john", "john@daeliin.com", "password").email).isEqualTo("john@daeliin.com");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenClearPasswordIsNull() {
        new SignUpRequest("john", "john@daeliin.com", null);
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