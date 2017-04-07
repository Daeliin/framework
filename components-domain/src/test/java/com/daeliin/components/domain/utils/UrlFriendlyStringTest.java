package com.daeliin.components.domain.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlFriendlyStringTest {

    @Test
    public void handlesNullAndBlankStrings() {
        assertEquals(new UrlFriendlyString(null).value, "");
        assertEquals(new UrlFriendlyString("").value, "");
        assertEquals(new UrlFriendlyString(" ").value, "");
    }
    
    @Test
    public void putsEveryCharactersToLowerCase() {
        assertEquals(new UrlFriendlyString("ThiSiSATeST").value, "thisisatest");
    }
    
    @Test
    public void deletesSpecialCharacters() {
        assertEquals(new UrlFriendlyString("test#ezt!a").value, "testezta");
    }
    
    @Test
    public void replacesSpaceWithDash() {
        assertEquals(new UrlFriendlyString("this is a test").value, "this-is-a-test");
    }
}
