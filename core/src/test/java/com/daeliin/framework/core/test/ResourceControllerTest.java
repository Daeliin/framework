package com.daeliin.framework.core.test;

import com.daeliin.framework.commons.test.IntegrationTest;
import com.daeliin.framework.core.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@ComponentScan(basePackages = "com.daeliin.framework.core")
@WebAppConfiguration
public class ResourceControllerTest extends IntegrationTest {
    
    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isOk());
    }
}
