package com.daeliin.framework.commons.security.cryptography;

import org.apache.commons.codec.digest.DigestUtils;

public class Sha512 implements DigestAlgorithm {

    @Override
    public String digest(final String data) {
        return DigestUtils.sha512Hex(data);
    }
}
