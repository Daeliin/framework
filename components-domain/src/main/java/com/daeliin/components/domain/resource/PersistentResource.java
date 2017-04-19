package com.daeliin.components.domain.resource;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Resource saved in a RDBMS and identified by an UUID,
 * Equality is only based on the UUID.
 * @param <ID> resource ID
 */
public abstract class PersistentResource<ID> implements Persistable<ID> {
    
    private static final LocalDateTime DEFAULT_CREATION_DATE = LocalDateTime.now();

    protected final ID id;
    protected final LocalDateTime creationDate;

    protected PersistentResource(ID id, LocalDateTime creationDate) {
        this.id = Objects.requireNonNull(id, "id should not be null");
        this.creationDate = creationDate != null ? creationDate : DEFAULT_CREATION_DATE;
    }

    public ID id() {
        return id;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentResource that = (PersistentResource) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.hashCode());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("creationDate", creationDate);
    }
}
