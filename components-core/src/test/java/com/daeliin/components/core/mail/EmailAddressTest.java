package com.daeliin.components.core.mail;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddressTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenNullAddress() {
        String nullAddress = null;
        new EmailAddress(nullAddress);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenBlankkAddress() {
        new EmailAddress(" ");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenInvalidAddress() {
        new EmailAddress("a@");
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
