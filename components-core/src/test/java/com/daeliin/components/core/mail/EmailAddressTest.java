package com.daeliin.components.core.mail;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddressTest {

    @Test(expected = MailBuildingException.class)
    public void shouldThrowException_whenNullAddress() throws Exception {
        String nullAddress = null;
        new EmailAddress(nullAddress);
    }
    
    @Test(expected = MailBuildingException.class)
    public void shouldThrowException_whenBlankkAddress() throws Exception {
        new EmailAddress(" ");
    }
    
    @Test(expected = MailBuildingException.class)
    public void shouldThrowException_whenInvalidAddress() throws Exception {
        new EmailAddress("a@");
    }
    
    @Test
    public void toString_containsAddress() throws Exception {
        assertThat(new EmailAddress("john.doe@example.com").value).contains("john.doe@example.com");
    }

    @Test
    public void shouldBeEqual_whenAddressesAreEqual() throws Exception {
        EmailAddress johnDoe = new EmailAddress("john.doe@example.com");
        EmailAddress johnDoeAsWell = new EmailAddress("john.doe@example.com");

        assertThat(johnDoe).isEqualTo(johnDoeAsWell);
    }

    @Test
    public void shouldPrintItsAdress() throws Exception {
        EmailAddress johnDoe = new EmailAddress("john.doe@example.com");

        assertThat(johnDoe.toString()).contains(johnDoe.value);
    }
}
