package com.daeliin.components.test;

import org.junit.Before;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * Provides a set up for integration testing of a resource endpoint.
 */
@WebAppConfiguration
public abstract class IntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Inject
    protected WebApplicationContext webApplicationContext;
    
    protected MockMvc mockMvc;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
