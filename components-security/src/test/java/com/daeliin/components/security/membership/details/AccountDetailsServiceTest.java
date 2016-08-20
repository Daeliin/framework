package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountRepository;
import com.daeliin.components.security.exception.InvalidTokenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class AccountDetailsServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountDetailsService accountDetailsService;
    
    @Test
    public void exists_null_returnsFalse() {
        assertFalse(accountDetailsService.exists(null));
    }
    
    @Test
    public void exists_nonExistingAccount_returnsFalse() {
        Account nonExistingAccount = new Account();
        nonExistingAccount.setUsername("nonExistingAccount");
        
        assertFalse(accountDetailsService.exists(nonExistingAccount));
    }
    
    @Test
    public void exists_existingAccount_returnsTrue() {
        Account existingAccount = accountRepository.findOne(1L);
        
        assertTrue(accountDetailsService.exists(existingAccount));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void signUp_nullAccount_throwsIllegalArgumentException() {
        accountDetailsService.signUp(null);
    }
    
    @Test
    public void signUp_account_setsAllMembershipFieldsOnAccount() {
        Account account = new Account();
        account.setClearPassword("clearPassword");
        
        accountDetailsService.signUp(account);
        
        assertNotNull(account.getSignUpDate());
        assertNotNull(account.getPassword());
        assertNotNull(account.getToken());
        assertFalse(account.isEnabled());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void tokensAreNotTheSame_nullAccount_throwsIllegalArgumentException() {
        accountDetailsService.tokensAreNotTheSame(null, "token");
    }
    
    @Test
    public void tokensAreNotTheSame_sameTokens_returnsFalse() {
        Account account = new Account();
        account.setToken("token");
        
        assertFalse(accountDetailsService.tokensAreNotTheSame(account, "token"));
    }
    
    @Test
    public void tokensAreNotTheSame_nullToken_returnsTrue() {
        Account account = new Account();
        account.setToken("token");
        
        assertTrue(accountDetailsService.tokensAreNotTheSame(account, null));
    }
    
    @Test
    public void tokensAreNotTheSame_differentTokens_returnsTrue() {
        Account account = new Account();
        account.setToken("token");
        
        assertTrue(accountDetailsService.tokensAreNotTheSame(account, "differentToken"));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void assignNewToken_nullAccount_throwsIllegalArgumentException() {
        accountDetailsService.assignNewToken(null);
    }
    
    @Test
    public void assignNewToken_accountWithoutToken_assignsANewTokenToTheAccount() {
        Account account = new Account();
        
        accountDetailsService.assignNewToken(account);
        
        assertNotNull(account.getToken());
    }
    
    @Test
    public void assignNewToken_accountWithToken_assignsANewTokenToTheAccount() {
        Account account = new Account();
        account.setToken("token");
        
        accountDetailsService.assignNewToken(account);
        
        assertNotNull(account.getToken());
        assertNotEquals(account.getToken(), "token");
    }
    
    @Test
    public void activate_invalidToken_doesntActivateAccount() {
        Account account = new Account();
        account.setEnabled(false);
        account.setToken("token");
        
        try {
            accountDetailsService.activate(account, "differentToken");
        } catch(InvalidTokenException e) {
        }
        
        assertFalse(account.isEnabled());
    }
    
    @Test
    public void activate_validToken_assignsANewTokenToAccount() {
        Account account = new Account();
        account.setEnabled(false);
        account.setToken("token");
        
        try {
            accountDetailsService.activate(account, "token");
        } catch(InvalidTokenException e) {
            fail();
        }
        
        assertNotNull(account.getToken());
        assertNotEquals(account.getToken(), "token");
    }
    
    @Test
    public void activate_validToken_activatesAccount() {
        Account account = new Account();
        account.setEnabled(false);
        account.setToken("token");
        
        try {
            accountDetailsService.activate(account, "token");
        } catch(InvalidTokenException e) {
            fail();
        }
        
        assertTrue(account.isEnabled());
    }
    
    @Test
    public void resetPassword_invalidToken_doesntResetPassword() {
        Account account = new Account();
        account.setToken("token");
        account.setPassword("password");
        
        try {
            accountDetailsService.resetPassword(account, "differentToken", "newPassword");
        } catch (InvalidTokenException e) {
        }
        
        assertEquals(account.getPassword(), "password");
    }
    
    @Test
    public void resetPassword_validToken_resetPassword() {
        Account account = new Account();
        account.setToken("token");
        account.setPassword("password");
        
        try {
            accountDetailsService.resetPassword(account, "token", "newPassword");
        } catch (InvalidTokenException e) {
            fail();
        }
        
        assertFalse(StringUtils.isBlank(account.getPassword()));
        assertNotEquals(account.getPassword(), "password");
    }
    
    @Test
    public void resetPassword_validToken_assignsNewTokenToAccount() {
        Account account = new Account();
        account.setToken("token");
        account.setPassword("password");
        
        try {
            accountDetailsService.resetPassword(account, "token", "newPassword");
        } catch (InvalidTokenException e) {
            fail();
        }
        
        assertNotNull(account.getToken());
        assertNotEquals(account.getToken(), "token");
    }
}
