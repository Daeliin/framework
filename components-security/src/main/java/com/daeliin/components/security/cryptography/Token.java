package com.daeliin.components.security.cryptography;

import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
public class Token {
    
    public final String asString;
    
    /**
     * Builds a token based on list of datas, according to a digest algorithm, random flag can be added to add random salt.
     * @param datas datas to generate the token instantiate
     * @param algorithm digest algorithm 
     * @param random whether random salt should be added or not
     */
    public Token(final List<String> datas, final DigestAlgorithm algorithm, final boolean random) {
        StringBuilder dataBuilder = new StringBuilder();

        if (random) {
            dataBuilder.append(new Date());
        }

        datas.forEach(dataBuilder::append);

        this.asString = algorithm.digest(dataBuilder.toString());
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
}
