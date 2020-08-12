package com.blebail.components.core.security.cryptography;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5 implements DigestAlgorithm {

    @Override
    public String digest(final String data) {
        return DigestUtils.md5Hex(data);
    }
    
    @Override
    public String toString() {
        return "Digest Algorithm (Md5)";
    }
}
