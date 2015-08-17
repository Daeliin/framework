package com.daeliin.framework.sample;

import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class UsersIntegrationTest extends SecuredIntegrationTest {
    
    @Test
    public void request_unauthenticated_returnsHttpUnauthorized() throws Exception {
        mockMvc
            .perform(get("/api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
}
