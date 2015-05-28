package com.daeliin.framework.commons.model;

/**
 * Entity saved in a RDBMS.
 */
public interface PersistentEntity {
   
    Long getId();

    void setId(Long id);
}
