package com.daeliin.components.security.fixtures;

import com.daeliin.components.security.sql.BPermission;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class PermissionFixtures {

    public static BPermission admin() {
        return new BPermission(
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "ADMIN",
                "ADMIN");
    }

    public static BPermission user() {
        return new BPermission(
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "USER",
                "USER");
    }

    public static BPermission moderator() {
        return new BPermission(
                LocalDateTime.of(2017, 5, 5, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "MODERATOR",
                "MODERATOR");
    }
}
