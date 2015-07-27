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
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class ResourceControllerTest extends IntegrationTest {
    
    @Autowired
    private UserRepository repository;

    @Test
    public void create_validResource_returnsHttpCreatedAndPersistedResource() throws Exception {
        User user = new User().withName("Test");
        
        MvcResult result = mockMvc
            .perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new JsonString(user).value()))
            .andExpect(status().isCreated())
            .andReturn();
        
        User persistedUser = new JsonObject<>(result.getResponse().getContentAsString(), User.class).value().get();
        assertNotNull(persistedUser.getId());
    }
}
