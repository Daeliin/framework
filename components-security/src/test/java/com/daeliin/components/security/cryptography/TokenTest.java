package com.daeliin.components.security.cryptography;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest {
    
    @Test
    public void shouldBuildANonRandomSha512TokenByDefault() {
        String token = new Token(new ArrayList<>(Arrays.asList("data"))).asString;
        assertThat(token).isEqualTo(DigestUtils.sha512Hex("data"));
    }
    
    @Test
    public void shouldBuildAMd5Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Md5()).asString;
        
        assertThat(token).isEqualTo(DigestUtils.md5Hex("data"));
    }
    
    @Test
    public void shouldBuildARandomMd5Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Md5(),
                true).asString;
        
        assertThat(token).isNotEqualTo(DigestUtils.md5Hex("data"));
        assertThat(token.length()).isEqualTo(32);
    }
    
    @Test
    public void shouldBuildASha512Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Sha512()).asString;
        
        assertThat(token).isEqualTo(DigestUtils.sha512Hex("data"));
    }
    
    @Test
    public void shouldBuildsRandomSha512Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Sha512(),
                true).asString;
        
        assertThat(token).isNotEqualTo(DigestUtils.sha512Hex("data"));
        assertThat(token.length()).isEqualTo(128);
    }
}
