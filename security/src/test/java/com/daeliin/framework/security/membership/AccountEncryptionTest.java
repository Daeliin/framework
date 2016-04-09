package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.credentials.account.Account;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class AccountEncryptionTest {
    
    @Test
    public void generatePassword_validPassword_returnsBcryptString() {
        Account account = createAccount("username", "12345678");
        AccountEncryption userDetailsEncryption = new AccountEncryption(account);
        
        assertEquals(userDetailsEncryption.password().length(), 60);
        assertTrue(userDetailsEncryption.password().startsWith("$2a$"));
    }
    
    @Test
    public void generateToken_validUsername_returnsSha512StringNotEqualToSha512OfUsername() {
        Account account = createAccount("username", "12345678");
        AccountEncryption userDetailsEncryption = new AccountEncryption(account);
        
        assertEquals(userDetailsEncryption.token().length(), 128);
        assertNotEquals(userDetailsEncryption.token(), DigestUtils.sha512Hex(account.getUsername()));
    }
    
    private Account createAccount(final String username, final String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setClearPassword(password);
    
        return account;
    }
}
