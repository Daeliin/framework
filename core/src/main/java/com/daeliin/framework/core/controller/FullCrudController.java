package com.daeliin.framework.core.controller;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * Exposes CRUD operations and pagination for a resource.
 * @author baptiste
 * @param <E> resource type
 * @param <ID> resource id type
 */
public interface FullCrudController<E extends PersistentResource, ID extends Serializable> {
    
    /**
     * Exposes a create entry point.
     * @param resource resource to create
     * @return created resource
     */
    E create(E resource);
    
    /**
     * Exposes a search by id entry point.
     * @param id resource id
     * @return resource
     */
    E getOne(ID id);
    
    /**
     * Exposes a pagination entry point.
     * @param pageNumber page number
     * @param pageSize page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     */
    Page<E> getAll(int pageNumber, int pageSize, Sort.Direction direction, String... properties);
    
    /**
     * Exposes an update entry point.
     * @param resource resource to update
     * @return updated resource 
     */
    E update(E resource);
    
    /**
     * Exposes a delete by id entry point.
     * @param id resource id to delete
     */
    void delete(ID id);
    
    /**
     * Exposes a delete entry point.
     */
    void deleteAll();
}
