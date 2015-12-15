package com.daeliin.framework.core.resource.json;

import com.daeliin.framework.core.resource.json.JsonObject;
import com.daeliin.framework.core.resource.mock.User;
import java.util.Optional;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class JsonObjectTest {
    
    @Test
    public void newJsonObject_resourceAsJsonString_returnsResource() {
        String userJsonString = "{\"id\":1,\"name\":\"John\"}";
        User expectedUser = new User().withId(1L).withName("John");
        User user = new JsonObject<>(userJsonString, User.class).value().get();
        
        assertEquals(user, expectedUser);
    }
    
    @Test
    public void newJsonObject_null_returnsEmpty() {
        assertEquals(new JsonObject<>(null, Object.class).value(), Optional.empty());
    }
}
