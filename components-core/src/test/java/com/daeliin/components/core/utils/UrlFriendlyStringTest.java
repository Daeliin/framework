package com.daeliin.components.core.utils;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class UrlFriendlyStringTest {

    @Test
    public void putsEveryCharactersToLowerCase() {
        assertEquals(new UrlFriendlyString("ThiS iS A TeST").value(), "this-is-a-test");
    }
    
    @Test
    public void replacesSpecialCharacters() {
        assertEquals(new UrlFriendlyString("test#ezt!a").value(), "testezta");
    }
    
    @Test
    public void replacesSpaces() {
        assertEquals(new UrlFriendlyString("this is a test").value(), "this-is-a-test");
    }
}
