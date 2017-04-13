package com.daeliin.components.core.resource.service;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.resource.repository.PagingRepository;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.PersistentResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Provides CRUD operations and pagination for a resource, with caching.
 * @param <E> resource type
 * @param <R> resource repository
 */
@Service
public abstract class ResourceService<E extends PersistentResource, R extends PagingRepository<E>> implements PagingService<E> {

    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Resource was not found";

    @Inject
    protected R repository;

    /**
     * Creates a resource.
     * @param resource resource to create
     * @return created resource
     */
    @Override
    public E create(E resource) {
        return repository.save(resource);
    }

    /**
     * Creates multiple resources.
     * @param resources resources to create
     * @return created resources
     */
    @Override
    public Iterable<E> create(Collection<E> resources) {
        return repository.save(resources);
    }

    /**
     * Returns true if the resource exists, false otherwise
     * @param resourceId id
     * @return true of the resource exists, false otherwise
     */
    @Override
    public boolean exists(Long resourceId) {
        if(resourceId == null) {
            return false;
        }

        return repository.exists(resourceId);
    }

    /**
     * Returns the total number of resources.
     * @return total number of resources
     */
    @Override
    public long count() {
        return repository.count();
    }

    /**
     * Finds a resource by its id.
     * @param resourceId resource id
     * @return resource
     * @throws PersistentResourceNotFoundException if the resource is not found
     */
    @Override
    public E findOne(Long resourceId) {
        E resource = null;

        if (resourceId != null) {
            resource = repository.findOne(resourceId);
        }

        if (resource == null) {
            throw new PersistentResourceNotFoundException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return resource;
    }

    /**
     * Finds every resources.
     * @return every resources
     */
    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a page of resources.
     * @param pageRequest resource page request
     * @return resource page
     */
    @Override
    public Page<E> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    /**
     * Finds multiple resources by their ids.
     * @param resourcesIds resources ids
     * @return resources
     */
    @Override
    public Iterable<E> findAll(Collection<Long> resourcesIds) {
        return repository.findAll(resourcesIds);
    }

    /**
     * Updates a resource.
     * @param resourceId resource id
     * @param resource resource to update
     * @return updated resource
     * @throws PersistentResourceNotFoundException if the resource id is not found or if the resource id doesnt match its actual id
     */
    @Override
    public E update(Long resourceId, E resource) {
        if (resourceId == null || !repository.exists(resourceId) || resource == null || !resource.id().equals(resourceId)) {
            throw new PersistentResourceNotFoundException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return repository.save(resource);
    }

    /**
     * Updates multiple resources.
     * @param resources resources to update
     * @return updated resources
     */
    @Override
    public Iterable<E> update(Collection<E> resources) {
        return repository.save(resources);
    }

    /**
     * Delete a resource by its id.
     * @param id id of the resource to delete
     * @throws PersistentResourceNotFoundException if the resource is not found
     */
    @Override
    public void delete(Long id) {
        if (!repository.exists(id)) {
            throw new PersistentResourceNotFoundException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        repository.delete(id);
    }

    /**
     * Delete resources by their ids.
     * @param resourceIds resources ids
     */
    @Override
    public void delete(Collection<Long> resourceIds) {
        if (!CollectionUtils.isEmpty(resourceIds)) {
            repository.delete(resourceIds);
        }
    }

    /**
     * Deletes a resource.
     * @param resource resource to delete
     */
    @Override
    public void delete(E resource) {
        repository.delete(resource.id());
    }

    /**
     * Deletes all resources.
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
