package com.daeliin.components.security.fixtures;

import com.daeliin.components.security.sql.BPermission;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class PermissionFixtures {

    public static BPermission admin() {
        return new BPermission(
                Timestamp.valueOf(LocalDateTime.of(2017, 5, 5, 12, 0, 0)),
                "ADMIN",
                "ADMIN");
    }

    public static BPermission user() {
        return new BPermission(
                Timestamp.valueOf(LocalDateTime.of(2017, 5, 5, 12, 0, 0)),
                "USER",
                "USER");
    }

    public static BPermission moderator() {
        return new BPermission(
                Timestamp.valueOf(LocalDateTime.of(2017, 5, 5, 12, 0, 0)),
                "MODERATOR",
                "MODERATOR");
    }
}
