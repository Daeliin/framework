package com.daeliin.components.domain.pagination;

import com.google.common.base.MoreObjects;

import java.util.*;

/**
 * The request for a page, with a zero based index.
 * Page index, size, and sorts to be applied.
 */
public final class PageRequest implements Comparable<PageRequest> {

    public static final int DEFAULT_INDEX = 0;
    public static final int DEFAULT_SIZE = 25;

    public final int index;
    public final int size;
    public final int offset;
    public final Map<String, Sort.Direction> sorts;

    public PageRequest() {
        this(DEFAULT_INDEX);
    }

    public PageRequest(int index) {
        this(index, DEFAULT_SIZE);
    }

    public PageRequest(int index, int size) {
        this(index, size, new HashSet<>());
    }

    public PageRequest(int index, int size, Set<Sort> sorts) {
        this.index = (index >= 0 && index <= Integer.MAX_VALUE) ? index : DEFAULT_INDEX;
        this.size = (size >= 0 && size <= Integer.MAX_VALUE) ? size : DEFAULT_SIZE;
        this.offset = index * size;
        this.sorts = (sorts == null || sorts.isEmpty()) ? new HashMap<>() : buildSorts(sorts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequest that = (PageRequest) o;
        return index == that.index &&
                size == that.size &&
                Objects.equals(sorts, that.sorts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, size, sorts);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("index", index)
                .add("size", size)
                .add("sorts", sorts)
                .toString();
    }

    @Override
    public int compareTo(PageRequest other) {
        if (this.equals(other)) {
            return 0;
        }

        return Integer.compare(this.index, other.index);
    }

    private Map<String, Sort.Direction> buildSorts(Set<Sort> sorts) {
        Map<String, Sort.Direction> sortMapping = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        sorts
            .stream()
            .sorted()
            .forEach(sort -> {
                if (!sortMapping.containsKey(sort.property)) {
                    sortMapping.put(sort.property, sort.direction);
                }
            });

        return sortMapping;
    }
}
