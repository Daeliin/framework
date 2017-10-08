package com.daeliin.components.persistence.resource.service;

import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.resource.Conversion;
import com.daeliin.components.core.resource.Persistable;
import com.daeliin.components.persistence.resource.repository.CrudRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Provides CRUD operations and pagination for a resource, with caching.
 * @param <T> resource type
 * @param <R> row type
 * @param <ID> resource id type
 */
public abstract class ResourceService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>> implements PagingService<T, ID> {

    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Resource was not found";

    protected final P repository;
    protected final Conversion<T, R> conversion;

    public ResourceService(P repository, Conversion<T, R> conversion) {
        this.repository = Objects.requireNonNull(repository);
        this.conversion = Objects.requireNonNull(conversion);
    }

    /**
     * Creates a resource.
     * @param resource resource to create
     * @return created resource
     */
    @Override
    public T create(T resource) {
        if (repository.exists(resource.getId())) {
            throw new IllegalStateException("Resource should not already exist when creating it");
        }

        R createdRow = repository.save(conversion.map(resource));

        return conversion.instantiate(createdRow);
    }

    /**
     * Creates multiple resources.
     * @param resources resources to create
     * @return created resources
     */
    @Override
    public Collection<T> create(Collection<T> resources) {
        Collection<R> createdRows = repository.save(conversion.map(resources));

        return conversion.instantiate(createdRows);
    }

    /**
     * Returns true if the resource exists, false otherwise
     * @param resourceId id
     * @return true of the resource exists, false otherwise
     */
    @Override
    public boolean exists(ID resourceId) {
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
     * Returns the total number of resources according to a predicate
     * @param predicate the predicate
     * @return total number of resources matching the predicate
     */
    @Override
    public long count(Predicate predicate) {
        return repository.count(predicate);
    }

    /**
     * Finds a resource by its id.
     * @param resourceId resource id
     * @return resource
     * @throws NoSuchElementException if the resource is not found
     */
    @Override
    public T findOne(ID resourceId) {
        if (resourceId == null) {
            throw new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        Optional<R> resourceRow = repository.findOne(resourceId);

        R resource = resourceRow.orElseThrow(() -> new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND));

        return conversion.instantiate(resource);
    }

    @Override
    public T findOne(Predicate predicate) {
        Optional<R> resource = repository.findOne(predicate);

        if (!resource.isPresent()) {
            throw new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return conversion.instantiate(resource.get());
    }

    /**
     * Finds every resources.
     * @return every resources
     */
    @Override
    public Collection<T> findAll() {
        return new TreeSet<>(conversion.instantiate(repository.findAll()));
    }

    @Override
    public Collection<T> findAll(Predicate predicate) {
        return conversion.instantiate(repository.findAll(predicate));
    }

    /**
     * Finds a page of resources.
     * @param pageRequest resource page request
     * @return resource page
     */
    @Override
    public Page<T> findAll(PageRequest pageRequest) {
        Page<R> rowPage = repository.findAll(pageRequest);

        return new Page<>(conversion.instantiate(rowPage.items), rowPage.totalItems, rowPage.totalPages);
    }

    /**
     * Finds a page of resources according to a predicate.
     * @param predicate the predicate
     * @param pageRequest resource page request
     * @return resource page
     */
    @Override
    public Page<T> findAll(Predicate predicate, PageRequest pageRequest) {
        Page<R> rowPage = repository.findAll(predicate, pageRequest);

        return new Page<>(conversion.instantiate(rowPage.items), rowPage.totalItems, rowPage.totalPages);
    }

    /**
     * Finds multiple resources by their ids.
     * @param resourcesIds resources ids
     * @return resources
     */
    @Override
    public Collection<T> findAll(Collection<ID> resourcesIds) {
        return new TreeSet(conversion.instantiate(repository.findAll(resourcesIds)));
    }

    /**
     * Updates a resource.
     * @param resource resource to update
     * @return updated resource
     * @throws NoSuchElementException if the resource is not found
     */
    @Override
    public T update(T resource) {
        if (resource == null || !repository.exists(resource.getId())) {
            throw new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return conversion.instantiate(repository.save(conversion.map(resource)));
    }

    /**
     * Updates multiple resources.
     * @param resources resources to update
     * @return updated resources
     */
    @Override
    public Collection<T> update(Collection<T> resources) {
        Collection<R> mappedResources = conversion.map(resources);

        return conversion.instantiate(repository.save(mappedResources));
    }

    /**
     * Delete a resource by its id.
     * @param id id of the resource to delete
     */
    @Override
    public boolean delete(ID id) {
        if (id == null) {
            return false;
        }

        return repository.delete(id);
    }

    /**
     * Delete resources by their ids.
     * @param resourceIds resources ids
     */
    @Override
    public boolean delete(Collection<ID> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return false;
        }

        return repository.delete(resourceIds);
    }

    /**
     * Deletes a resource.
     * @param resource resource to delete
     */
    @Override
    public boolean delete(T resource) {
        if (resource == null ){
            return false;
        }

        return repository.delete(resource.getId());
    }

    /**
     * Deletes all resources.
     */
    @Override
    public boolean deleteAll() {
        return repository.deleteAll();
    }
}
