package com.daeliin.framework.core.controller;

import com.daeliin.framework.commons.test.IntegrationTest;
import com.daeliin.framework.core.Application;
import com.daeliin.framework.core.json.JsonObject;
import com.daeliin.framework.core.json.JsonString;
import com.daeliin.framework.core.mock.User;
import com.daeliin.framework.core.mock.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ResourceControllerTest extends IntegrationTest {
    
    @Autowired
    private UserRepository repository;
    
    @Test
    public void create_validResource_returnsHttpCreatedAndPersistedResource() throws Exception {
        User validUser = new User().withName("Test");
        
        MvcResult result = 
            mockMvc
                .perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonString(validUser).value()))
                .andExpect(status().isCreated())
                .andReturn();
        
        User persistedUser = new JsonObject<>(result.getResponse().getContentAsString(), User.class).value().get();
        assertNotNull(persistedUser.getId());
    }
    
    @Test
    public void create_invalidResource_returnsHttpBadREquest() throws Exception {
        User invalidUser = new User().withName("");
        
        mockMvc
            .perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(invalidUser).value()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void getOne_existingResource_returnsHttpOkAndResource() throws Exception {
        User existingUser = repository.findOne(1L);
        
        MvcResult result = 
            mockMvc
                .perform(get("/user/" + existingUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        User retrievedUser = new JsonObject<>(result.getResponse().getContentAsString(), User.class).value().get();
        assertEquals(retrievedUser, existingUser);
    }
    
    @Test
    public void getOne_nonExistingResource_returnsHttpNotfound() throws Exception {
        User nonExistingUser = new User().withId(-1L).withName("nameOfNonExistingUser");
                
        mockMvc
            .perform(get("/user/" + nonExistingUser.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
