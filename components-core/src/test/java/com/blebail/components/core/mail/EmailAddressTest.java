package com.blebail.components.core.mail;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailAddressTest {

    @Test
    public void shouldThrowException_whenNullAddress() {
        assertThrows(IllegalArgumentException.class, () -> new EmailAddress(null));
    }
    
    @Test
    public void shouldThrowException_whenBlankkAddress() {
        assertThrows(IllegalArgumentException.class, () -> new EmailAddress(" "));
    }
    
    @Test
    public void shouldThrowException_whenInvalidAddress() {
        assertThrows(IllegalArgumentException.class, () ->  new EmailAddress("a@"));
    }
    
    @Test
    public void toString_containsAddress() {
        assertThat(new EmailAddress("john.doe@example.com").value).contains("john.doe@example.com");
    }

    @Test
    public void shouldBeEqual_whenAddressesAreEqual() {
        EmailAddress johnDoe = new EmailAddress("john.doe@example.com");
        EmailAddress johnDoeAsWell = new EmailAddress("john.doe@example.com");

        assertThat(johnDoe).isEqualTo(johnDoeAsWell);
    }

    @Test
    public void shouldPrintItsAdress() {
        EmailAddress johnDoe = new EmailAddress("john.doe@example.com");

        assertThat(johnDoe.toString()).contains(johnDoe.value);
    }
}
