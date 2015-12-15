package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.security.membership.ResetPasswordRequest;
import com.daeliin.framework.commons.security.details.UserDetails;
import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import com.daeliin.framework.core.resource.json.JsonString;
import com.daeliin.framework.security.mock.Application;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import com.daeliin.framework.security.mock.user.model.User;
import com.daeliin.framework.security.mock.user.repository.UserRepository;
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

@ContextConfiguration(classes = Application.class)
public class MembershipIntegrationTest extends SecuredIntegrationTest {
    
    private static final String MEMBERSHIP_PATH = API_ROOT_PATH + "/public/membership";
    
    @Autowired
    private UserRepository userDetailsRepository;
    
    @Test
    public void signup_invalidEmail_returnsHttpBadRequest() throws Exception {
        UserDetails invalidEmailUserDetails = createUserDetails("username", "invalid.email", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidEmailUserDetails).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_invalidUsername_returnsHttpBadRequest() throws Exception {
        UserDetails invalidUsernameUserDetails = createUserDetails("a", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidUsernameUserDetails).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_invalidPassword_returnsHttpBadRequest() throws Exception {
        UserDetails invalidPasswordUserDetails = createUserDetails("username", "email@email.com", "123");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidPasswordUserDetails).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void signup_existingUserDetails_returnsHttpPreconditionFailed() throws Exception {
        UserDetails existingUserDetails = userDetailsRepository.findOne(1L);
        existingUserDetails.setClearPassword("password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(existingUserDetails).value()))
            .andExpect(status().isPreconditionFailed());
    }
    
    @Test
    public void signup_existingUserDetails_doesntCreateUserDetails() throws Exception {
        long userDetailsCountBeforeSignUp = userDetailsRepository.count();
        UserDetails existingUserDetails = userDetailsRepository.findOne(1L);
        existingUserDetails.setClearPassword("password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(existingUserDetails).value()));
        
        long userDetailsCountAfterSignUp = userDetailsRepository.count();
        
        assertEquals(userDetailsCountAfterSignUp, userDetailsCountBeforeSignUp);
    }
    
    @Test
    public void signup_validUserDetails_returnsHttpOk() throws Exception {
        UserDetails validUserDetails = createUserDetails("username", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(validUserDetails).value()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void signup_validUserDetails_createsUserDetails() throws Exception {
        UserDetails validUserDetails = createUserDetails("username", "email@email.com", "password");
        
        mockMvc
            .perform(post(MEMBERSHIP_PATH + "/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(validUserDetails).value()));
        
        UserDetails createdUserDetails = userDetailsRepository.findByUsernameIgnoreCase("username");
        
        assertEquals(createdUserDetails, validUserDetails);
        assertTrue(StringUtils.isNotBlank(createdUserDetails.getPassword()));
        assertTrue(StringUtils.isNotBlank(createdUserDetails.getToken()));
    }
    
    @Test
    public void activate_nonExistentUserDetailsId_returnsHttpNotFound() throws Exception {
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", "-1")
            .param("token", "token"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void activate_wrongActivationToken_returnsHttpUnauthorized() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(1L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", "wrong" + userDetails.getToken()))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void activate_wrongActivationToken_doesntActivateUserDetails() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", "wrong" + userDetails.getToken()));
        
        assertFalse(userDetails.isEnabled());
    }
    
    @Test
    public void activate_validUserDetailsIdAndActivationToken_returnsHttpOk() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void activate_validUserDetailsIdAndActivationToken_activatesUserDetails() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()));
        
        assertTrue(userDetails.isEnabled());
    }
    
    @Test
    public void activate_validUserDetailsIdAndActivationToken_setsNewToken() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(3L);
        String activationToken = userDetails.getToken();
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/activate")
            .param("id", String.valueOf(userDetails.getId()))
            .param("token", userDetails.getToken()));
        
        assertNotEquals(userDetails.getToken(), activationToken);
    }
    
    @Test
    public void newPassword_nonExistentUserDetailsId_returnsHttpNotFound() throws Exception {
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/newpassword")
            .param("id", "-1"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void newPassword_validUserDetailsId_returnsHttpOk() throws Exception {
        UserDetails userDetails = userDetailsRepository.findOne(3L);
        
        mockMvc
            .perform(get(MEMBERSHIP_PATH + "/newpassword")
            .param("id", String.valueOf(userDetails.getId())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void resetPassword_invalidUserDetailsId_returnsHttpBadRequest() throws Exception {
        ResetPasswordRequest invalidUserDetailsIdResetPasswordRequest = createResetPasswordRequest(null, "token", "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidUserDetailsIdResetPasswordRequest).value()))
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
    public void resetPassword_nonExistentUserDetailsId_returnsHttpNotFound() throws Exception {
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(-1L, "token", "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void resetPassword_wrongActivationToken_returnsHttpUnauthorized() throws Exception {
        User userDetails = userDetailsRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(userDetails.getId(), "wrong" + userDetails.getToken(), "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void resetPassword_wrongActivationToken_doesntResetUserDetailsPassword() throws Exception {
        User userDetails = userDetailsRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(userDetails.getId(), "wrong" + userDetails.getToken(), "newPassword");
        String tokenBeforeResetPassword = userDetails.getToken();
        String passwordBeforeResetPassword = userDetails.getPassword();
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()));
        
        String tokenAfterResetPassword = userDetails.getToken();
        String passwordAfterResetPassword = userDetails.getPassword();
        
        assertEquals(passwordAfterResetPassword, passwordBeforeResetPassword);
        assertEquals(tokenAfterResetPassword, tokenBeforeResetPassword);
        assertFalse(userDetails.isEnabled());
    }
    
    @Test
    public void resetPassword_validResetPasswordRequest_returnsHttpOk() throws Exception {
        User userDetails = userDetailsRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(userDetails.getId(), userDetails.getToken(), "newPassword");
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()))
            .andExpect(status().isOk());
    }
    
    @Test
    public void resetPassword_validResetPasswordRequest_resetUserDetailsPassword() throws Exception {
        User userDetails = userDetailsRepository.findOne(3L);
        ResetPasswordRequest resetPasswordRequest = createResetPasswordRequest(userDetails.getId(), userDetails.getToken(), "newPassword");
        String passwordBeforeResetPassword = userDetails.getPassword();
        String tokenBeforeResetPassword = userDetails.getToken();
        
        mockMvc
            .perform(put(MEMBERSHIP_PATH + "/resetpassword")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(resetPasswordRequest).value()));
        
        String tokenAfterResetPassword = userDetails.getToken();
        String passwordAfterResetPassword = userDetails.getPassword();
        
        assertNotEquals(passwordAfterResetPassword, passwordBeforeResetPassword);
        assertNotEquals(tokenAfterResetPassword, tokenBeforeResetPassword);
        assertTrue(userDetails.isEnabled());
    }
    
    private UserDetails createUserDetails(final String username, final String email, final String password) {
        UserDetails user = new User();
        
        user.setUsername(username);
        user.setEmail(email);
        user.setClearPassword(password);
        
        return user;
    }
    
    private ResetPasswordRequest createResetPasswordRequest(final Long id, final String token, final String newPassword) {  
        ResetPasswordRequest<Long> resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setUserDetailsId(id);
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setNewPassword(newPassword);
        
        return resetPasswordRequest;
    }
}
