package com.daeliin.framework.commons.security.cryptography;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TokenTest {
    
    @Test
    public void newToken_data_buildsANotRandomSha512TokenFromData() {
        String token = new Token(new ArrayList<>(Arrays.asList("data"))).asString();
        assertEquals(token, DigestUtils.sha512Hex("data"));
    }
    
    @Test
    public void newToken_dataAndMd5_buildsAMd5TokenFromData() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Md5()
                ).asString();
        
        assertEquals(token, DigestUtils.md5Hex("data"));
    }
    
    @Test
    public void newToken_dataAndMd5AndRandom_buildsARandomMd5Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Md5(),
                true
                ).asString();
        
        assertNotEquals(token, DigestUtils.md5Hex("data"));
        assertEquals(token.length(), 32);
    }
    
    @Test
    public void newToken_dataAndSha512_buildsASha512TokenFromdata() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Sha512()
                ).asString();
        
        assertEquals(token, DigestUtils.sha512Hex("data"));
    }
    
    @Test
    public void newToken_dataAndSha12AndRandom_buildsARandomSha512Token() {
        String token = 
            new Token(
                new ArrayList<>(Arrays.asList("data")),
                new Sha512(),
                true
                ).asString();
        
        assertNotEquals(token, DigestUtils.sha512Hex("data"));
        assertEquals(token.length(), 128);
    }
}
