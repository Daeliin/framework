package com.daeliin.framework.commons.test;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;

/**
 * Provides a set up for a secured integration testing of a resource endpoint.
 */
@WebAppConfiguration
public abstract class SecuredIntegrationTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired 
    protected WebApplicationContext webApplicationContext;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    protected MockMvc mockMvc;
    
    @BeforeMethod
    protected void setUpMethod() {
        mockMvc = 
            MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    };
}
