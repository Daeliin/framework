package com.daeliin.framework.core.controller;

import com.daeliin.framework.core.exception.PageRequestException;
import java.io.Serializable;
import org.springframework.data.domain.Page;

/**
 * Exposes CRUD operations and pagination for a resource.
 * @author baptiste
 * @param <E> resource type
 * @param <ID> resource id type
 */
public interface FullCrudController<E extends Serializable, ID extends Serializable> {
    
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
     * @throws PageRequestException if one of the parameter is not valid
     */
    Page<E> getAll(String pageNumber, String pageSize, String direction, String... properties) throws PageRequestException;
    
    /**
     * Exposes an update by id entry point.
     * @param id resource id
     * @param resource resource to update
     * @return updated resource 
     */
    E update(ID id, E resource);
    
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
