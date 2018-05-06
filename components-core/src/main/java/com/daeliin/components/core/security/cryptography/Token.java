package com.daeliin.components.core.security.cryptography;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Token {
    
    public final String asString;
    
    /**
     * Builds a token based on list of datas, according to a digest algorithm, random flag can be added to add random salt.
     * @param datas datas to generate the token from
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(asString, token.asString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asString);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("asString", asString)
            .toString();
    }
}
