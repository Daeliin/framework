package com.daeliin.components.domain.resource;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Resource saved in a RDBMS and identified by an ID,
 * Equality is only based on the UUID.
 */
public abstract class UUIDPersistentResource implements PersistentResource<Long> {
    
    private static final long serialVersionUID = -5886577401324234159L;
    
    private final Long id;
    private final String uuid;
    private final LocalDateTime creationDate;

    protected UUIDPersistentResource(Long id, String uuid, LocalDateTime creationDate) {
        this.id = Objects.requireNonNull(id, "id should not be null");
        this.uuid = Objects.requireNonNull(uuid, "uuid should not be null");
        this.creationDate = Objects.requireNonNull(creationDate, "creationDate should not be null");
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
        UUIDPersistentResource that = (UUIDPersistentResource) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid.hashCode());
    }

    @Override
    public String toString() {
        return "UUIDPersistentResource{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
