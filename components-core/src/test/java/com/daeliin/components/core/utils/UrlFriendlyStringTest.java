package com.daeliin.components.core.utils;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class UrlFriendlyStringTest {

    @Test
    public void putsEveryCharactersToLowerCase() {
        assertEquals(new UrlFriendlyString("ThiSiSATeST").value(), "thisisatest");
    }
    
    @Test
    public void deletesSpecialCharacters() {
        assertEquals(new UrlFriendlyString("test#ezt!a").value(), "testezta");
    }
    
    @Test
    public void replacesSpaceWithDash() {
        assertEquals(new UrlFriendlyString("this is a test").value(), "this-is-a-test");
    }
}
