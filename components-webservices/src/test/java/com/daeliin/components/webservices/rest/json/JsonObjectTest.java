package com.daeliin.components.webservices.rest.json;

import com.daeliin.components.webservices.mock.User;
import java.util.Optional;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class JsonObjectTest {
    
    @Test
    public void newJsonObject_resourceAsJsonString_returnsResource() {
        String userJsonString = "{\"id\":1,\"name\":\"John\",\"uuid\":\"e3b6e850-7c28-4b11-8ffa-f29c7957986c\"}";
        User expectedUser = new User().withId(1L).withName("John").withUuid("e3b6e850-7c28-4b11-8ffa-f29c7957986c");
        User user = new JsonObject<>(userJsonString, User.class).value().get();
        
        assertEquals(user, expectedUser);
    }
    
    @Test
    public void newJsonObject_null_returnsEmpty() {
        assertEquals(new JsonObject<>(null, Object.class).value(), Optional.empty());
    }
}
