package com.daeliin.components.core.resource.pagination;

import java.util.Objects;

public final class Sort {

    public final String property;

    public final Direction direction;

    public Sort(String property, Direction direction) {
        this.property = Objects.requireNonNull(property, "property should not be null");
        this.direction = direction != null ? direction : Direction.ASC;
    }

    public enum Direction {
        ASC, DESC
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort = (Sort) o;
        return Objects.equals(property, sort.property) &&
                direction == sort.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, direction);
    }
}
