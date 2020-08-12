package com.blebail.components.core.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnglishTitleTest {

    @Test
    public void handleBlankStrings() {
        assertThat(new EnglishTitle("").toUrlFriendlyString()).isEqualTo("");
        assertThat(new EnglishTitle(" ").toUrlFriendlyString()).isEqualTo("");
        assertThat(new EnglishTitle("    ").toUrlFriendlyString()).isEqualTo("");
    }
    
    @Test
    public void putsEveryCharactersToLowerCase() {
        assertThat(new EnglishTitle("ThiSiSATeST").toUrlFriendlyString()).isEqualTo("thisisatest");
    }
    
    @Test
    public void deletesSpecialCharacters() {
        assertThat(new EnglishTitle("test#ezt!a").toUrlFriendlyString()).isEqualTo("testezta");
    }
    
    @Test
    public void replacesSpaceWithDash() {
        assertThat(new EnglishTitle("this is a test").toUrlFriendlyString()).isEqualTo("this-is-a-test");
    }

    @Test
    public void replacesAmpersandWithAnd() {
        assertThat(new EnglishTitle("Down & Dirty").toUrlFriendlyString()).isEqualTo("down-and-dirty");
    }
}
