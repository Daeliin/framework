package com.daeliin.components.domain.pagination;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Objects;

/**
 * A page of items.
 * @param <E> type of paginated items
 */
public final class Page<E> {

    public final Collection<E> items;
    public final long nbElements;
    public final long totalElements;
    public final long totalPages;

    public Page(Collection<E> items, long totalElements, long totalPages) {
        this.items = Objects.requireNonNull(items, " items should not be null");
        this.nbElements = items.size();
        this.totalElements = (totalElements > 0L && totalElements < Long.MAX_VALUE) ? totalElements : 0L;
        this.totalPages = (totalPages > 1L && totalPages < Long.MAX_VALUE) ? totalPages : 1L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return totalElements == page.totalElements &&
                totalPages == page.totalPages &&
                Objects.equals(items, page.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalElements, totalPages);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("items", items)
                .add("nbElements", nbElements)
                .add("totalElements", totalElements)
                .add("totalPages", totalPages)
                .toString();
    }
}
