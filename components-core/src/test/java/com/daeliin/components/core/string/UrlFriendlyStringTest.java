package com.daeliin.components.core.string;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlFriendlyStringTest {

    @Test
    public void handlesNullAndBlankStrings() {
        assertThat(new UrlFriendlyString(null).value).isEqualTo("");
        assertThat(new UrlFriendlyString("").value).isEqualTo("");
        assertThat(new UrlFriendlyString(" ").value).isEqualTo("");
    }
    
    @Test
    public void putsEveryCharactersToLowerCase() {
        assertThat(new UrlFriendlyString("ThiSiSATeST").value).isEqualTo("thisisatest");
    }
    
    @Test
    public void deletesSpecialCharacters() {
        assertThat(new UrlFriendlyString("test#ezt!a").value).isEqualTo("testezta");
    }
    
    @Test
    public void replacesSpaceWithDash() {
        assertThat(new UrlFriendlyString("this is a test").value).isEqualTo("this-is-a-test");
    }

    @Test
    public void replacesAmpersandWithAnd() {
        assertThat(new UrlFriendlyString("Down & Dirty").value).isEqualTo("down-and-dirty");
    }
}
