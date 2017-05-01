//package com.daeliin.components.webservices.rest.json;
//
//import com.daeliin.components.webservices.mock.User;
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
//
//public class JsonStringTest {
//
//    @Test
//    public void newJsonString_resource_returnsResourceAsJsonString() {
//        User user = new User().withId(1L).withName("John").withUuid("e3b6e850-7c28-4b11-8ffa-f29c7957986c");
//        String expectedUserJsonString = "{\"id\":1,\"id\":\"e3b6e850-7c28-4b11-8ffa-f29c7957986c\",\"name\":\"John\"}";
//        String jsonString = new JsonString(user).value();
//
//        assertEquals(jsonString, expectedUserJsonString);
//    }
//
//    @Test
//    public void newJsonString_null_returnsNullString() {
//        String expectedUserJsonString = "null";
//        String jsonString = new JsonString(null).value();
//
//        assertEquals(jsonString, expectedUserJsonString);
//    }
//}
