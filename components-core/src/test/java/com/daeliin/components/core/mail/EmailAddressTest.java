package com.daeliin.components.core.mail;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

public class EmailAddressTest {

    @Test(expectedExceptions = MailBuildingException.class)
    public void new_nullAddress_throwsMailBuildingException() throws Exception {
        new EmailAddress(null);
    }
    
    @Test(expectedExceptions = MailBuildingException.class)
    public void new_blankAddress_throwsMailBuildingException() throws Exception {
        new EmailAddress(" ");
    }
    
    @Test(expectedExceptions = MailBuildingException.class)
    public void new_invalidAddress_throwsMailBuildingException() throws Exception {
        new EmailAddress("a@");
    }
    
    @Test
    public void new_validAddress_doesntThrowAnyException() throws Exception {
        try {
            new EmailAddress("john.doe@example.com");
        } catch(Exception e) {
            fail();
        }
    }
    
    @Test
    public void toString_containsAddress() throws Exception {
        assertTrue(new EmailAddress("john.doe@example.com").toString().contains("john.doe@example.com"));
    }
}
