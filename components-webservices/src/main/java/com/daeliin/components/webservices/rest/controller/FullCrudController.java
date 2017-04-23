package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.webservices.exception.PageRequestException;

import java.io.Serializable;
import java.util.Collection;

/**
 * Exposes CRUD operations and pagination for a resource.
 * @author baptiste
 * @param <E> resource type
 */
public interface FullCrudController<E extends Serializable> {
    
    /**
     * Exposes a create entry point.
     * @param resource resource to create
     * @return created resource
     */
    E create(E resource);
    
    /**
     * Exposes a search by id entry point.
     * @param resourceId resource id
     * @return resource
     */
    E getOne(String resourceId);
    
    /**
     * Exposes a pagination entry point.
     * @param page page index
     * @param size page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws PageRequestException if one of the parameter is not valid
     */
    Page<E> getAll(String page, String size, String direction, String... properties) throws PageRequestException;
    
    /**
     * Exposes an update by id entry point.
     * @param resourceId resource id
     * @param resource resource to update
     * @return updated resource 
     */
    E update(String resourceId, E resource);
    
    /**
     * Exposes a delete by id entry point.
     * @param resourceId resource id to delete
     */
    void delete(String resourceId);
    
    /**
     * Exposes a delete entry point for a list of ids.
     * @param resourceIds resources ids to delete
     */
    void delete(Collection<String> resourceIds);
}
