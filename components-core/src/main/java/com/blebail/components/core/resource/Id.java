package com.blebail.components.core.resource;

import java.util.UUID;

public final class Id {

    public static String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
