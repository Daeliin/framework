package com.daeliin.components.core.resource;

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
    ID getId();

    /**
     * Returns the resource creation date.
     * @return the resource creation date
     */
    Instant getCreationDate();
}
