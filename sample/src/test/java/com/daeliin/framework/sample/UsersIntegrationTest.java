package com.daeliin.framework.sample;

import com.daeliin.framework.commons.test.IntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class UsersIntegrationTest extends IntegrationTest {
    
    @Test
    public void findAll_returnsUsersPage1() throws Exception {
        mockMvc
            .perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }
}
