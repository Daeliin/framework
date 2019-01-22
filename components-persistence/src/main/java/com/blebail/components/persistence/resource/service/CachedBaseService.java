package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.resource.Conversion;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.components.persistence.resource.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * {@inheritDoc}
 * Caches resources in memory.
 */
public abstract class CachedBaseService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>>
        extends BaseService<T, R, ID, P> implements CachedService {

    protected final Map<ID, T> cache;

    public CachedBaseService(P repository, Conversion<T, R> conversion) {
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
        if (resourceId == null) {
            return false;
        }

        loadCacheIfNecessary();

        return cache.containsKey(resourceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        loadCacheIfNecessary();

        return cache.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(ID resourceId) {
        if (resourceId == null) {
            throw new IllegalArgumentException();
        }

        loadCacheIfNecessary();

        return Optional.ofNullable(cache.get(resourceId))
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll() {
        loadCacheIfNecessary();

        return new TreeSet<>(cache.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll(Collection<ID> resourcesIds) {
        loadCacheIfNecessary();

        return cache.values().stream()
                .filter(resource -> resourcesIds.contains(resource.id()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T resource) {
        T updatedResource = super.update(resource);

        cache.put(resource.id(), resource);

        return updatedResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> update(Collection<T> resources) {
        Collection<T> updatedResources = super.update(resources);

        updatedResources.forEach(updatedResource -> cache.put(updatedResource.id(), updatedResource));

        return updatedResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(ID id) {
        boolean deleted = super.delete(id);

        if (deleted) {
            cache.remove(id);
        }

        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Collection<ID> resourceIds) {
        boolean deleted = super.delete(resourceIds);

        if (deleted) {
            resourceIds.forEach(cache::remove);
        }

        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(T resource) {
        boolean deleted = super.delete(resource);

        if (deleted) {
            cache.remove(resource.id());
        }

        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {
        boolean deleted = super.deleteAll();

        cache.clear();

        return deleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidate() {
        cache.clear();
        
        super.findAll().forEach(resource -> cache.put(resource.id(), resource));
    }

    private void loadCacheIfNecessary() {
        if (cache.isEmpty()) {
            invalidate();
        }
    }
}
