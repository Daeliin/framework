package com.daeliin.components.security.credentials.permission;


import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Permission implements Comparable<Permission> {
    
    public final String label;

    public Permission(String label) {
        this.label = Objects.requireNonNull(label, "label should not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .toString();
    }

    @Override
    public int compareTo(Permission other) {
        if (this.equals(other)) {
            return 0;
        }
        
        return this.label.compareTo(other.label);
    }
}
