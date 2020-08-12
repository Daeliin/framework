package com.blebail.components.core.pagination;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * A direction for a property.
 */
public final class Sort implements Comparable<Sort> {

    /**
     * Direction of sort, either ascending or descending.
     */
    public enum Direction {
        ASC, DESC
    }

    public final String property;
    public final Direction direction;

    public Sort(String property) {
        this(property, Direction.ASC);
    }

    public Sort(String property, Direction direction) {
        this.property = Objects.requireNonNull(property);
        this.direction = direction != null ? direction : Direction.ASC;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("property", property)
                .add("direction", direction)
                .toString();
    }

    @Override
    public int compareTo(Sort other) {
        if (this.equals(other)) {
            return 0;
        }

        int propertyCompare = property.compareTo(other.property);

        if (propertyCompare != 0) {
            return propertyCompare;
        }

        return direction.compareTo(other.direction);
    }
}
