package com.daeliin.components.domain.resource;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Resource saved in a RDBMS and identified by an ID,
 * Equality is only based on the UUID.
 */
public abstract class PersistentResource implements Persistable {
    
    private static final long serialVersionUID = -5886577401324234159L;

    private static final LocalDateTime DEFAULT_CREATION_DATE = LocalDateTime.now();

    protected final Long id;
    protected final String uuid;
    protected final LocalDateTime creationDate;

    protected PersistentResource(Long id, String uuid, LocalDateTime creationDate) {
        this.id = Objects.requireNonNull(id, "id should not be null");
        this.uuid = Objects.requireNonNull(uuid, "uuid should not be null");
        this.creationDate = creationDate != null ? creationDate : DEFAULT_CREATION_DATE;
    }

    public Long id() {
        return id;
    }

    public String uuid() {
        return uuid;
    }

    public LocalDateTime creationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentResource that = (PersistentResource) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid.hashCode());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("uuid", uuid)
                .add("creationDate", creationDate);
    }
}
