package com.daeliin.framework.security.authentication.form;

import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import com.daeliin.framework.security.mock.Application;
import static com.daeliin.framework.security.mock.Application.API_ROOT_PATH;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class FormAuthenticationIntegrationTest extends SecuredIntegrationTest {
    
    @Value("${authentication.endpoint}")
    private String authenticationEndpoint;
    
    @Value("${authentication.logout.endpoint}")
    private String authenticationLogoutEndpoint;
    
    @Value("${authentication.username.parameter}")
    private String authenticationAccountnameParameter;
    
    @Value("${authentication.password.parameter}")
    private String authenticationPasswordParameter;
    
    @Test
    public void request_unauthenticated_returnsHttpUnauthorized() throws Exception {
        mockMvc
            .perform(get(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void authenticate_invalidCredentials_returnsHttpForbidden() throws Exception {
        login("invalidAccount", "invalidPassword").andExpect(status().isForbidden());
    }
    
    @Test
    public void authenticate_invalidCredentials_isNotLoggedIn() throws Exception {
        login("invalidAccount", "invalidPassword");
        
        mockMvc
            .perform(get(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void authenticate_validCredentials_returnsHttpOk() throws Exception {
        login("John", "password").andExpect(status().isOk());
    }
    
    @Test
    public void authenticate_loginIsCaseInsensitive() throws Exception {
        login("john", "password").andExpect(status().isOk());
    }
    
    @WithMockUser
    @Test
    public void authenticated_canRequestSecuredResources() throws Exception {
        mockMvc
            .perform(get(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    public void logout_returnsHttpOk() throws Exception {
        login("John", "password");
        logout().andExpect(status().isOk());
    }
    
    @Test
    public void logout_isLoggedOut() throws Exception {
        login("John", "password");
        logout();
        
        mockMvc
            .perform(get(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
    
    @WithMockUser(roles = "USER")
    @Test
    public void accountWithoutAdminPermissions_requestsResourcesProtectedByAdmin_returnsHttpForbidden() throws Exception {
        mockMvc
            .perform(delete(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }
    
    @WithMockUser(roles = "ADMIN")
    @Test
    public void accountWithAdminPermissions_requestsResourcesProtectedByAdmin_returnsCorrespondingHttpCode() throws Exception {
        mockMvc
            .perform(delete(API_ROOT_PATH + "/accounts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isGone());
    } 
    
    private ResultActions login(final String username, final String password) throws Exception {
        return 
            mockMvc
                .perform(post(API_ROOT_PATH + authenticationEndpoint)
                .param(authenticationAccountnameParameter, username)
                .param(authenticationPasswordParameter, password));
    }
    
    private ResultActions logout() throws Exception {
        return 
            mockMvc
                .perform(post(API_ROOT_PATH + authenticationLogoutEndpoint));
    }
}
