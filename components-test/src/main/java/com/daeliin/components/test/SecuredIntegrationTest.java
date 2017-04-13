package com.daeliin.components.test;

import org.junit.Before;
import javax.inject.Inject;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Provides a set up for a secured integration testing of a resource endpoint.
 */
@TestExecutionListeners(listeners = {WithSecurityContextTestExecutionListener.class})
@WebAppConfiguration
public abstract class SecuredIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Inject
    protected WebApplicationContext webApplicationContext;
    
    protected MockMvc mockMvc;
    
    @Before
    public void setUp() {
        mockMvc = 
            MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
}
