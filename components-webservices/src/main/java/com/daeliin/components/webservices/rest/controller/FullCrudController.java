package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.webservices.exception.PageRequestException;

import java.util.Collection;

/**
 * Exposes CRUD operations and pagination for a resource.
 * @author baptiste
 * @param <T> resource type
 */
public interface FullCrudController<T, ID> {
    
    /**
     * Exposes a create entry point.
     * @param resource resource to create
     * @return created resource
     */
    T create(T resource);
    
    /**
     * Exposes a search by id entry point.
     * @param resourceId resource id
     * @return resource
     */
    T getOne(ID resourceId);
    
    /**
     * Exposes a pagination entry point.
     * @param page page index
     * @param size page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws PageRequestException if one of the parameter is not valid
     */
    Page<T> getAll(String page, String size, String direction, String... properties) throws PageRequestException;
    
    /**
     * Exposes an update by id entry point.
     * @param resourceId resource id
     * @param resource resource to update
     * @return updated resource 
     */
    T update(ID resourceId, T resource);
    
    /**
     * Exposes a delete by id entry point.
     * @param resourceId resource id to delete
     */
    void delete(ID resourceId);
    
    /**
     * Exposes a delete entry point for a list of ids.
     * @param resourceIds resources ids to delete
     */
    void delete(Collection<ID> resourceIds);
}
