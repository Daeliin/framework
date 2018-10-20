package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.resource.Conversion;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.components.persistence.resource.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * {@inheritDoc}
 * Caches resources in memory.
 */
public abstract class CachedResourceService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>>
        extends BaseService<T, R, ID, P> implements CachedService<ID> {

    protected final Map<ID, T> cache;

    public CachedResourceService(P repository, Conversion<T, R> conversion) {
        super(repository, conversion);
        cache = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T resource) {
        T createdResource = super.create(resource);

        cache.put(createdResource.id(), createdResource);

        return createdResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> create(Collection<T> resources) {
        Map<ID, T> createdResources = super.create(resources)
                .stream()
                .collect(toMap(Persistable::id, Function.identity()));

        cache.putAll(createdResources);

        return createdResources.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(ID resourceId) {
        if (cache.isEmpty()) {
            invalidate();
        }

        return cache.containsKey(resourceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        if (cache.isEmpty()) {
            invalidate();
        }

        return cache.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(ID resourceId) {
        if (!cache.containsKey(resourceId)) {
            invalidate();
        }

        return cache.get(resourceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll() {
        if (cache.isEmpty()) {
            invalidate();
        }

        return cache.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll(Collection<ID> resourcesIds) {
        if (cache.isEmpty()) {
            invalidate();
        }

        return cache.values().stream()
                .filter(resource -> resourcesIds.contains(resource.id()))
                .collect(Collectors.toSet());
    }

    @Override
    public T update(T resource) {
        T updatedResource = super.update(resource);

        invalidate();

        return updatedResource;
    }

    @Override
    public Collection<T> update(Collection<T> resources) {
        Collection<T> updatedResources = super.update(resources);

        invalidate();

        return updatedResources;
    }

    @Override
    public boolean delete(ID id) {
        boolean deleted = super.delete(id);

        invalidate();

        return deleted;
    }

    @Override
    public boolean delete(Collection<ID> resourceIds) {
        boolean deleted = super.delete(resourceIds);

        invalidate();

        return deleted;
    }

    @Override
    public boolean delete(T resource) {
        boolean deleted = super.delete(resource);

        invalidate();

        return deleted;
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = super.deleteAll();

        invalidate();

        return deleted;
    }

    @Override
    public void invalidate() {
        cache.clear();
        
        super.findAll().forEach(resource -> cache.put(resource.id(), resource));
    }
}
