package com.daeliin.components.security.library;

import com.daeliin.components.security.credentials.permission.Permission;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class PermissionLibrary {

    public static Permission admin() {
        return new Permission(
                "ADMIN",
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "ADMIN");
    }

    public static Permission user() {
        return new Permission(
                "USER",
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "USER");
    }

    public static Permission moderator() {
        return new Permission(
                "MODERATOR",
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "MODERATOR");
    }
}

