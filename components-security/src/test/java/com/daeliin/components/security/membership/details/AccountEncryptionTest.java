//package com.daeliin.components.security.membership.details;
//
//import com.daeliin.components.security.credentials.account.Account;
//import org.apache.commons.codec.digest.DigestUtils;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import org.junit.Test;
//
//public class AccountEncryptionTest {
//
//    @Test
//    public void generatePassword_validPassword_returnsBcryptString() {
//        Account account = createAccount("username", "12345678");
//        AccountEncryption userDetailsEncryption = new AccountEncryption(account);
//
//        assertEquals(userDetailsEncryption.password().length(), 60);
//        assertTrue(userDetailsEncryption.password().startsWith("$2a$"));
//    }
//
//    @Test
//    public void generateToken_validUsername_returnsSha512StringNotEqualToSha512OfUsername() {
//        Account account = createAccount("username", "12345678");
//        AccountEncryption userDetailsEncryption = new AccountEncryption(account);
//
//        assertEquals(userDetailsEncryption.token().length(), 128);
//        assertNotEquals(userDetailsEncryption.token(), DigestUtils.sha512Hex(account.getUsername()));
//    }
//
//    private Account createAccount(final String username, final String password) {
//        Account account = new Account();
//        account.setUsername(username);
//        account.setClearPassword(password);
//
//        return account;
//    }
//}
