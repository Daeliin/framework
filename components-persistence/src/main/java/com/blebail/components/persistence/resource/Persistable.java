package com.blebail.components.persistence.resource;

import java.time.Instant;

/**
 * Resource persistable in a RDBMS, with an id.
 * @param <ID> resource ID
 */
public interface Persistable<ID> {

    /**
     * Returns the resource id.
     * @return the resource id
     */
    ID id();

    /**
     * Returns the resource creation date.
     * @return the resource creation date
     */
    Instant creationDate();
}
