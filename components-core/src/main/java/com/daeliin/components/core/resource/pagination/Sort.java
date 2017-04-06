package com.daeliin.components.core.resource.pagination;

import java.util.Objects;

public final class Sort {

    public final String property;

    public final Direction direction;

    public Sort(String property, Direction direction) {
        this.property = Objects.requireNonNull(property, "property must not be null");
        this.direction = direction != null ? direction : Direction.ASC;
    }

    public enum Direction {
        ASC, DESC
    }
}
