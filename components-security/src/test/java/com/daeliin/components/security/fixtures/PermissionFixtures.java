package com.daeliin.components.security.fixtures;

import com.daeliin.components.security.sql.BPermission;

public final class PermissionFixtures {

    public static BPermission admin() {
        return new BPermission("ADMIN");
    }

    public static BPermission user() {
        return new BPermission("USER");
    }

    public static BPermission moderator() {
        return new BPermission("MODERATOR");
    }
}
