package com.daeliin.framework.commons.model;

import java.io.Serializable;

/**
 * Entity saved in a RDBMS.
 * @param <ID> id column type
 */
public interface PersistentResource<ID extends Serializable> {
    
    ID getId();

    void setId(ID id);
}
