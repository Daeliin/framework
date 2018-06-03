package com.daeliin.components.cms.library;

import com.daeliin.components.cms.credentials.permission.Permission;

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

    public static Permission publisher() {
        return new Permission(
                "PUBLISHER",
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "PUBLISHER");
    }
}

