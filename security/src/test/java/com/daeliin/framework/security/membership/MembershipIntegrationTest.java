package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.membership.ResetPasswordRequest;
import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import com.daeliin.framework.core.resource.json.JsonString;
import com.daeliin.framework.security.Application;
import static com.daeliin.framework.security.Application.API_ROOT_PATH;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import com.daeliin.framework.commons.security.credentials.account.AccountRepository;

@ContextConfiguration(classes = Application.class)
public class MembershipIntegrationTest extends SecuredIntegrationTest {
    
    private static final String MEMBERSHIP_PATH = API_ROOT_PATH + "/public/membership";
    
    @Autowired
    private AccountRepository persistentAccountRepository;
    
    @Test
    public void signup_invalidEmail_returnsHttpBadRequest() throws Exception {
        Account invalidEmailAccount = createAccount("username", "invalid.email", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidEmailAccount).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_invalidUsername_returnsHttpBadRequest() throws Exception {
        Account invalidUsernameAccount = createAccount("a", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidUsernameAccount).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_invalidPassword_returnsHttpBadRequest() throws Exception {
        Account invalidPasswordAccount = createAccount("username", "email@email.com", "123");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidPasswordAccount).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_existingAccount_returnsHttpPreconditionFailed() throws Exception {
        Account existingAccount = persistentAccountRepository.findOne(1L);
        existingAccount.setClearPassword("password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(existingAccount).value()))
            .andExpect(status().isPreconditionFailed());
    }
    
    @Test
    public void signup_existingAccount_doesntCreateAccount() throws Exception {
        long userDetailsCountBeforeSignUp = persistentAccountRepository.count();
        Account existingAccount = persistentAccountRepository.findOne(1L);
        existingAccount.setClearPassword("password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(existingAccount).value()));
        
        long userDetailsCountAfterSignUp = persistentAccountRepository.count();
        
        assertEquals(userDetailsCountAfterSignUp, userDetailsCountBeforeSignUp);
    }
    
    @Test
    public void signup_validAccount_returnsHttpOk() throws Exception {
        Account validAccount = createAccount("username", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(validAccount).value()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void signup_validAccount_createsAccount() throws Exception {
        Account validAccount = createAccount("username", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(validAccount).value()));
        
        Account createdAccount = persistentAccountRepository.findByUsernameIgnoreCase("username");
        
        assertEquals(createdAccount, validAccount);
        assertTrue(StringUtils.isNotBlank(createdAccount.getPassword()));
        assertTrue(StringUtils.isNotBlank(createdAccount.getToken()));
    }
    
    @Test
    public void activate_nonExistentAccountId_returnsHttpNotFound() throws Exception {
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", "-1")
            .param("token", "token"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void activate_wrongActivationToken_returnsHttpUnauthorized() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(1L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", "wrong" + userDetails.getToken()))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void activate_wrongActivationToken_doesntActivateAccount() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", "wrong" + userDetails.getToken()));
        
        assertFalse(userDetails.isEnabled());
    }
    
    @Test
    public void activate_validAccountIdAndActivationToken_returnsHttpOk() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void activate_validAccountIdAndActivationToken_activatesAccount() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()));
        
        assertTrue(userDetails.isEnabled());
    }
    
    @Test
    public void activate_validAccountIdAndActivationToken_setsNewToken() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(3L);
        String activationToken = userDetails.getToken();
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()));
        
        assertNotEquals(userDetails.getToken(), activationToken);
    }
    
    @Test
    public void newPassword_nonExistentAccountId_returnsHttpNotFound() throws Exception {
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/newpassword")
            .param("id", "-1"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void newPassword_validAccountId_returnsHttpOk() throws Exception {
        Account userDetails = persistentAccountRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/newpassword")
            .param("id", String.valueOf(userDetails.getId())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void resetPassword_invalidAccountId_returnsHttpBadRequest() throws Exception {
        ResetPasswordRequest invalidAccountIdResetPasswordRequest = createResetPasswordRequest(null, "token", "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidAccountIdResetPasswordRequest).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void resetPassword_invalidToken_returnsHttpBadRequest() throws Exception {
        ResetPasswordRequest invalidTokenResetPasswordRequest = createResetPasswordRequest(3L, " ", "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidTokenResetPasswordRequest).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void resetPassword_invalidNewPassword_returnsHttpBadRequest() throws Exception {
        ResetPasswordRequest invalidNewPasswordResetPasswordRequest = createResetPasswordRequest(3L, "token", " ");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidNewPasswordResetPasswordRequest).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void resetPassword_nonExistentAccountId_returnsHttpNotFound() throws Exception {
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(-1L, "token", "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void resetPassword_wrongActivationToken_returnsHttpUnauthorized() throws Exception {
        Account account = persistentAccountRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(account.getId(), "wrong" + account.getToken(), "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void resetPassword_wrongActivationToken_doesntResetAccountPassword() throws Exception {
        Account account = persistentAccountRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(account.getId(), "wrong" + account.getToken(), "newPassword");
        String tokenBeforeResetPassword = account.getToken();
        String passwordBeforeResetPassword = account.getPassword();
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()));
        
        String tokenAfterResetPassword = account.getToken();
        String passwordAfterResetPassword = account.getPassword();
        
        assertEquals(passwordAfterResetPassword, passwordBeforeResetPassword);
        assertEquals(tokenAfterResetPassword, tokenBeforeResetPassword);
        assertFalse(account.isEnabled());
    }
    
    @Test
    public void resetPassword_validResetPasswordRequest_returnsHttpOk() throws Exception {
        Account account = persistentAccountRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(account.getId(), account.getToken(), "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void resetPassword_validResetPasswordRequest_resetAccountPassword() throws Exception {
        Account account = persistentAccountRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(account.getId(), account.getToken(), "newPassword");
        String passwordBeforeResetPassword = account.getPassword();
        String tokenBeforeResetPassword = account.getToken();
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()));
        
        String tokenAfterResetPassword = account.getToken();
        String passwordAfterResetPassword = account.getPassword();
        
        assertNotEquals(passwordAfterResetPassword, passwordBeforeResetPassword);
        assertNotEquals(tokenAfterResetPassword, tokenBeforeResetPassword);
        assertTrue(account.isEnabled());
    }
    
    private Account createAccount(final String username, final String email, final String password) {
        Account account = new Account();
        
        account.setUsername(username);
        account.setEmail(email);
        account.setClearPassword(password);
        
        return account;
    }
    
    private ResetPasswordRequest createResetPasswordRequest(final Long id, final String token, final String newPassword) {  
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setAccountId(id);
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setNewPassword(newPassword);
        
        return resetPasswordRequest;
    }
}
