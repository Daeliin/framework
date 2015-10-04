package com.daeliin.framework.commons.security.cryptography;

import java.util.Date;
import java.util.List;

public class Token {
    
    private final List<String> datas;
    private final DigestAlgorithm algorithm;
    private final boolean random;
    private String asString;
    
    /**
     * Builds a token based on list of datas, according to a digest algorithm, random flag can be added to add random salt.
     * @param datas datas to generate the token from
     * @param algorithm digest algorithm 
     * @param random whether random salt should be added or not
     */
    public Token(final List<String> datas, final DigestAlgorithm algorithm, final boolean random) {
        this.datas = datas;
        this.algorithm = algorithm;
        this.random = random;
        this.asString = "";
        
        generate();
    }
    
    public Token(final List<String> datas, final boolean random) {
        this(datas, new Sha512(), random);
    }
    
    public Token(final List<String> datas, final DigestAlgorithm algorithm) {
        this(datas, algorithm, false);
    }
    
    public Token(final List<String> datas) {
        this(datas, new Sha512(), false);
    }
    
    /**
     * Returns the token as a string.
     * @return the token as  a string
     */
    public String asString() {
        return this.asString;
    }
    
    private void generate() {
        StringBuilder dataBuilder = new StringBuilder();
        
        if (random) {
            dataBuilder.append(new Date());
        }
        
        datas.forEach(dataBuilder::append);
    
        asString = algorithm.digest(dataBuilder.toString());
    }
}
