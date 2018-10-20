package com.blebail.components.webservices.controller;

import java.util.Collection;

/**
 * Exposes CRUD operations and pagination for a resource.
 * @param <T> resource type
 */
public interface CrudController<T, ID> {
    
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
