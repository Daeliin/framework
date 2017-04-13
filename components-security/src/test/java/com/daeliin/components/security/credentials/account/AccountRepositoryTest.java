package com.daeliin.components.security.credentials.account;

import com.daeliin.components.security.Application;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(classes = Application.class)
public class AccountRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountRepository accountRepository;
    
    @Test
    public void findByEmailIgnoreCase_null_returnsNull() {
        assertNull(accountRepository.findByEmailIgnoreCase(null));
    }
    
    @Test
    public void findByEmailIgnoreCase_nonExistingEmail_returnsNull() {
        assertNull(accountRepository.findByEmailIgnoreCase("noway@thisemail.exists"));
    }
    
    @Test
    public void findByEmailIgnoreCase_existingEmail_returnsCorrespondingAccount() {
        String existingEmail = "admin@daeliin.com";
        Account account = accountRepository.findByEmailIgnoreCase(existingEmail);
        
        assertNotNull(account);
        assertEquals(account.getEmail(), existingEmail);
    }
    
    @Test
    public void findByEmailIgnoreCase_isNotCaseSensitive() {
        String existingEmail = "AdmIn@daEliiN.cOm";
        Account account = accountRepository.findByEmailIgnoreCase(existingEmail);
        
        assertNotNull(account);
        assertEquals(account.getEmail(), existingEmail.toLowerCase());
    }
    
    @Test
    public void findByUsernameIgnoreCase_null_returnsNull() {
        assertNull(accountRepository.findByUsernameIgnoreCase(null));
    }
    
    @Test
    public void findByUsernameIgnoreCase_nonExistingUsername_returnsNull() {
        assertNull(accountRepository.findByUsernameIgnoreCase("nowaythisusernameexists"));
    }
    
    @Test
    public void findByUsernameIgnoreCase_existingUsername_returnsCorrespondingAccount() {
        String existingUsername = "admin";
        Account account = accountRepository.findByUsernameIgnoreCase(existingUsername);
        
        assertNotNull(account);
        assertEquals(account.getUsername(), existingUsername);
    }
    
    @Test
    public void findByUsernameIgnoreCase_isNotCaseSensitive() {
        String existingUsername = "AdMiN";
        Account account = accountRepository.findByUsernameIgnoreCase(existingUsername);
        
        assertEquals(account.getUsername(), existingUsername.toLowerCase());
    }
    
    @Test
    public void findByEnabled_true_returnsOnlyEnabledAccounts() {
        accountRepository.findByEnabled(true).forEach(account -> {
            assertTrue(account.isEnabled());
        });
    }
    
    @Test
    public void findByEnabled_false_returnsOnlyNotEnabledAccounts() {
        accountRepository.findByEnabled(false).forEach(account -> {
            assertFalse(account.isEnabled());
        });
    }
}
