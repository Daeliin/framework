package com.daeliin.components.security.credentials.permission;


import com.daeliin.components.core.resource.PersistentResource;

import java.time.Instant;
import java.util.Objects;

public class Permission extends PersistentResource<String> implements Comparable<Permission> {

    public final String name;

    public Permission(String id, Instant creationDate, String name) {
        super(id, creationDate);
        this.name = Objects.requireNonNull(name, "name should not be null");
    }

    public String toString() {
        return toStringHelper()
                .add("name", name)
                .toString();
    }

    @Override
    public int compareTo(Permission other) {
        if (this.equals(other)) {
            return 0;
        }
        
        return this.name.compareTo(other.name);
    }
}
