package com.daeliin.framework.commons.security.cryptography;

import java.util.Collection;

public class Token {
    
    public static enum ALGORITHM {
        MD5, SHA512
    }
    
    private final Collection<String> datas;
    private final ALGORITHM algorithm;
    private final boolean random;
    private String asString;
    
    public Token(final Collection<String> datas, final ALGORITHM algorithm, final boolean random) {
        this.datas = datas;
        this.algorithm = algorithm;
        this.random = random;
        
        generate();
    }
    
    public Token(final Collection<String> datas) {
        this(datas, ALGORITHM.SHA512, false);
    }
    
    public String asString() {
        return this.asString;
    }
    
    private void generate() {
        this.asString = "generated";
    }
}
