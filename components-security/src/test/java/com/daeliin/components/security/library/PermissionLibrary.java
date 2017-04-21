package com.daeliin.components.security.library;

import com.daeliin.components.security.credentials.permission.Permission;

public final class PermissionLibrary {

    public static Permission admin() {
        return new Permission("ADMIN");
    }

    public static Permission user() {
        return new Permission("USER");
    }
}

