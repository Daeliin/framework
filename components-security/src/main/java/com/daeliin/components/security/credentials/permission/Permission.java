package com.daeliin.components.security.credentials.permission;


import com.daeliin.components.domain.resource.PersistentResource;
import com.google.common.base.MoreObjects;

import java.time.Instant;
import java.util.Objects;

public class Permission extends PersistentResource<String> implements Comparable<Permission> {

    public final String name;

    public Permission(String id, Instant creationDate, String name) {
        super(id, creationDate);
        this.name = Objects.requireNonNull(name, "name should not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
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
