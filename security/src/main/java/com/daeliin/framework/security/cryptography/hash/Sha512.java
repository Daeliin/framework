package com.daeliin.framework.security.cryptography.hash;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha512 implements DigestAlgorithm {

    @Override
    public String digest(final String data) {
        return DigestUtils.sha512Hex(data);
    }
    
    @Override
    public String toString() {
        return "Digest Algorithm (Sha512)";
    }
}
