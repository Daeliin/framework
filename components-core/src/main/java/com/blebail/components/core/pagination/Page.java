package com.blebail.components.core.pagination;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Objects;

/**
 * A page of items.
 * @param <E> type of paginated items
 */
public final class Page<E> {

    public final Collection<E> items;
    public final long nbItems;
    public final long totalItems;
    public final long totalPages;

    public Page(Collection<E> items, long totalItems, long totalPages) {
        this.items = Objects.requireNonNull(items);
        this.nbItems = items.size();
        this.totalItems = (totalItems > 0L && totalItems < Long.MAX_VALUE) ? totalItems : 0L;
        this.totalPages = (totalPages > 1L && totalPages < Long.MAX_VALUE) ? totalPages : 1L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return totalItems == page.totalItems &&
                totalPages == page.totalPages &&
                Objects.equals(items, page.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalItems, totalPages);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("items", items)
                .add("nbItems", nbItems)
                .add("totalitems", totalItems)
                .add("totalPages", totalPages)
                .toString();
    }
}
