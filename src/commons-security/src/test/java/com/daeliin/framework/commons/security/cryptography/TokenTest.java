package com.daeliin.framework.commons.security.cryptography;

import java.util.ArrayList;
import java.util.Arrays;
import org.aspectj.weaver.ast.Test;

public class TokenTest {
    
    @Test
    public void newToken_default_buildsANotRandomSha512Token() {
        Token token = new Token(new ArrayList<String>(Arrays.asList({"username", "username@mail.com"})));
    }
}
