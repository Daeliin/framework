package com.blebail.components.persistence.resource.service;

import com.blebail.components.persistence.resource.Persistable;

import java.util.Collection;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <T> resource type
 * @param <ID> resource ID type
 */
public interface CrudService<T extends Persistable<ID>, ID> extends PagingService<T, ID> {

    /**
     * Creates a resource.
     * @param resource resource to create
     * @return created resource
     */
    T create(T resource);

    /**
     * Creates multiple resources.
     * @param resources resources to create
     * @return created resources
     */
    Collection<T> create(Collection<T> resources);

    /**
     * Updates a resource.
     * @param resource resource to update
     * @return updated resource
     */
    T update(T resource);

    /**
     * Updates multiple resources.
     * @param resources resources to update
     * @return updated resources
     */
    Collection<T> update(Collection<T> resources);

    /**
     * Finds a resource by its id.
     * @param resourceId resource id
     * @return resource
     */
    T findOne(ID resourceId);

    /**
     * Finds every resources.
     * @return every resources
     */
    Collection<T> findAll();

    /**
     * Finds multiple resources by their ids.
     * @param resourceIds resources ids
     * @return resources
     */
    Collection<T> findAll(Collection<ID> resourceIds);

    /**
     * Returns true if the resource exists, false otherwise
     * @param resourceId resource id
     * @return true of the resource exists, false otherwise
     */
    boolean exists(ID resourceId);

    /**
     * Returns the total number of resources.
     * @return total number of resources
     */
    long count();

    /**
     * Delete a resource by its id.
     * @param resourceId id of the resource to delete
     */
    boolean delete(ID resourceId);

    /**
     * Delete resources by their ids.
     * @param resourceIds resources ids
     * @return true if resources were deleted, false otherwise
     */
    boolean delete(Collection<ID> resourceIds);

    /**
     * Deletes a resource.
     * @param resource resource to delete
     * @return true if the resource was deleted, false otherwise
     */
    boolean delete(T resource);

    /**
     * Deletes all resources.
     */
    boolean deleteAll();
}
