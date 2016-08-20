package com.daeliin.components.domain.resource;

import java.io.Serializable;

/**
 * Resource persisted in a RDBMS.
 * @param <ID> id type
 */
public interface PersistentResource<ID extends Serializable> extends Serializable {

    /**
     * Returns the resource id.
     * @return resource id
     */
    ID getId();
    
    /**
     * Sets the resource id.
     * @param id the new resource id
     */
    void setId(Long id);
}
