package com.daeliin.components.domain.resource;

import java.io.Serializable;

/**
 * Resource persisted in a RDBMS, with an id and an uuid.
 * @param <ID> id type
 */
public interface PersistentResource<ID extends Serializable> extends Serializable {

    /**
     * Returns the resource id.
     * @return resource id
     */
    ID id();

    /**
     * Returns the resource UUID.
     * @return the resource UUID
     */
    String uuid();
}
