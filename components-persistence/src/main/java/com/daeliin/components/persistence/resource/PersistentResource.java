package com.daeliin.components.persistence.resource;

import com.google.common.base.MoreObjects;

import java.time.Instant;
import java.util.Objects;

/**
 * Resource saved in a RDBMS and identified by an id,
 * Equality is only based on the id.
 * @param <ID> resource ID type
 */
public abstract class PersistentResource<ID> implements Persistable<ID> {
    
    private final ID id;
    private final Instant creationDate;

    protected PersistentResource(ID id) {
        this(id, Instant.now());
    }

    protected PersistentResource(ID id, Instant creationDate) {
        this.id = Objects.requireNonNull(id);
        this.creationDate = Objects.requireNonNull(creationDate);
    }

    public ID getId() {
        return id;
    }

    public Instant getCreationDate() {
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
