package com.daeliin.framework.security.membership;

import com.daeliin.framework.security.mock.user.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class UserDetailsEncryptionTest {
    
    @Test
    public void generatePassword_validPassword_returnsBcryptString() {
        User user = createUser("username", "12345678");
        UserDetailsEncryption userDetailsEncryption = new UserDetailsEncryption(user);
        
        assertEquals(userDetailsEncryption.password().length(), 60);
        assertTrue(userDetailsEncryption.password().startsWith("$2a$"));
    }
    
    @Test
    public void generateToken_validUsername_returnsSha512StringNotEqualToSha512OfUsername() {
        User user = createUser("username", "12345678");
        UserDetailsEncryption userDetailsEncryption = new UserDetailsEncryption(user);
        
        assertEquals(userDetailsEncryption.token().length(), 128);
        assertNotEquals(userDetailsEncryption.token(), DigestUtils.sha512Hex(user.getUsername()));
    }
    
    private User createUser(final String username, final String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
    
        return user;
    }
}
