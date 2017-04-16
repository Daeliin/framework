package com.daeliin.components.domain.resource;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Resource persistable in a RDBMS, with an uuid.
 */
public interface Persistable extends Serializable {

    /**
     * Returns the resource UUID.
     * @return the resource UUID
     */
    String uuid();

    /**
     * Returns the resource creation date.
     * @return the resource creation date
     */
    LocalDateTime creationDate();
}
