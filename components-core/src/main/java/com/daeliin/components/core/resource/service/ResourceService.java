package com.daeliin.components.core.resource.service;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.resource.repository.PagingRepository;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.domain.resource.Persistable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toSet;

/**
 * Provides CRUD operations and pagination for a resource, with caching.
 * @param <T> resource type
 * @param <R> row type
 * @param <ID> resource id type
 */
public abstract class ResourceService<T extends Persistable<ID>, R, ID, P extends PagingRepository<R, ID>> implements PagingService<T, ID> {

    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Resource was not found";

    protected final P repository;
    protected final Conversion<T, R> conversion;

    public ResourceService(P repository, Conversion<T, R> conversion) {
        this.repository = repository;
        this.conversion = conversion;
    }

    /**
     * Creates a resource.
     * @param resource resource to create
     * @return created resource
     */
    @Override
    public T create(T resource) {
        R createdRow = repository.save(map(resource));

        return instantiate(createdRow);
    }

    /**
     * Creates multiple resources.
     * @param resources resources to create
     * @return created resources
     */
    @Override
    public Collection<T> create(Collection<T> resources) {
        Collection<R> createdRows = repository.save(map(resources));

        return instantiate(createdRows);
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
     * Finds a resource by its id.
     * @param resourceId resource id
     * @return resource
     * @throws PersistentResourceNotFoundException if the resource is not found
     */
    @Override
    public T findOne(ID resourceId) {
        T resource = null;

        if (resourceId != null) {
            resource = instantiate(repository.findOne(resourceId));
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
    public Collection<T> findAll() {
        return new TreeSet<>(instantiate(repository.findAll()));
    }

    /**
     * Finds a page of resources.
     * @param pageRequest resource page request
     * @return resource page
     */
    @Override
    public Page<T> findAll(PageRequest pageRequest) {
        Page<R> rowPage = repository.findAll(pageRequest);

        return new Page<>(instantiate(rowPage.items), rowPage.totalItems, rowPage.totalPages);
    }

    /**
     * Finds multiple resources by their ids.
     * @param resourcesIds resources ids
     * @return resources
     */
    @Override
    public Collection<T> findAll(Collection<ID> resourcesIds) {
        return new TreeSet(instantiate(repository.findAll(resourcesIds)));
    }

    /**
     * Updates a resource.
     * @param resource resource to update
     * @return updated resource
     * @throws PersistentResourceNotFoundException if the resource is not found
     */
    @Override
    public T update(T resource) {
        if (resource == null || !repository.exists(resource.id())) {
            throw new PersistentResourceNotFoundException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return instantiate(repository.save(map(resource)));
    }

    /**
     * Updates multiple resources.
     * @param resources resources to update
     * @return updated resources
     */
    @Override
    public Collection<T> update(Collection<T> resources) {
        Collection<R> mappedResources = map(resources);

        return instantiate(repository.save(mappedResources));
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

        return repository.delete(resource.id());
    }

    /**
     * Deletes all resources.
     */
    @Override
    public boolean deleteAll() {
        return repository.deleteAll();
    }

    public R map(T resource) {
        return conversion.map(resource);
    }

    public Set<R> map(Collection<T> resources) {
        return resources
                .stream()
                .map(conversion::map)
                .collect(toSet());
    }

    public T instantiate(R row) {
        return conversion.instantiate(row);
    }

    public Set<T> instantiate(Collection<R> rows) {
        return rows
                .stream()
                .map(conversion::instantiate)
                .collect(toSet());
    }
}
