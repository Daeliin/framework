package com.daeliin.framework.commons.model;

import java.io.Serializable;

/**
 * Entity saved in a RDBMS.
 */
public interface PersistentResource extends Serializable {
    
    Long getId();

    PersistentResource setId(Long id);
}
