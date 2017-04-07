package com.daeliin.components.core.resource.service;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.PersistentResource;
import java.io.Serializable;
import java.util.List;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 */
public interface PagingService<E extends PersistentResource, ID extends Serializable> {

    /**
     * Creates a resource.
     * @param resource resource to create
     * @return created resource
     */
    E create(E resource);

    /**
     * Creates multiple resources.
     * @param resources resources to create
     * @return created resources
     */
    Iterable<E> create(Iterable<E> resources);

    /**
     * Updates a resource by id.
     * @param id resource id
     * @param resource resource to update
     * @return updated resource
     */
    E update(ID id, E resource);

    /**
     * Updates multiple resources.
     * @param resources resources to update
     * @return updated resources
     */
    Iterable<E> update(Iterable<E> resources);

    /**
     * Finds a resource by its id.
     * @param id resource id
     * @return resource
     */
    E findOne(ID id);

    /**
     * Finds every resources.
     * @return every resources
     */
    Iterable<E> findAll();

    /**
     * Finds a page of resources.
     * @param pageRequest resource page request
     * @return resource page
     */
    Page<E> findAll(PageRequest pageRequest);

    /**
     * Finds multiple resources by their ids.
     * @param resources resources ids
     * @return resources
     */
    Iterable<E> findAll(Iterable<ID> resources);

    /**
     * Returns true if the resource exists, false otherwise
     * @param id resource id
     * @return true of the resource exists, false otherwise
     */
    boolean exists(ID id);

    /**
     * Returns the total number of resources.
     * @return total number of resources
     */
    long count();

    /**
     * Delete a resource by its id.
     * @param id id of the resource to delete
     */
    void delete(ID id);

    /**
     * Delete resources by their ids.
     * @param ids resources ids
     */
    void delete(List<ID> ids);

    /**
     * Deletes a resource.
     * @param resource resource to delete
     */
    void delete(E resource);

    /**
     * Deletes multiple resources.
     * @param resources resources to delete
     */
    void delete(Iterable<? extends E> resources);

    /**
     * Deletes all resources.
     */
    void deleteAll();
}
