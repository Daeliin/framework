package com.daeliin.components.core.resource.pagination;

import java.util.Objects;

public final class Page<E> {

    public final Iterable<E> items;
    public final long elementCount;
    public final long totalElementCount;
    public final long totalPageCount;

    public Page(Iterable<E> items, long elementCount, long totalElementCount, long totalPageCount) {
        this.items = Objects.requireNonNull(items, " items should not be null");
        this.elementCount = (elementCount < 0L || elementCount > Long.MAX_VALUE) ? elementCount : 0L;
        this.totalElementCount = (totalElementCount < 0L || totalElementCount > Long.MAX_VALUE) ? totalElementCount : 0L;
        this.totalPageCount = (totalPageCount < 1L || totalPageCount > Long.MAX_VALUE) ? totalPageCount : 1L;
    }
}
