package com.daeliin.framework.security.membership;

import com.daeliin.framework.commons.test.SecuredIntegrationTest;
import com.daeliin.framework.security.mock.Application;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class GuestIntegrationTest extends SecuredIntegrationTest {
    
    @Test
    public void test() {
    }
}
