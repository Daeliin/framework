package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.resource.Conversion;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.querydsl.crud.sync.repository.CrudRepository;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeSet;

/**
 * {@inheritDoc}
 */
public abstract class ResourceService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>>
        extends BaseService<T, R, ID, P> implements CrudService<T, ID> {

    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Resource was not found";

    public ResourceService(P repository, Conversion<T, R> conversion) {
        super(repository, conversion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T resource) {
        if (repository.exists(resource.id())) {
            throw new IllegalStateException("Resource should not already exist when creating it");
        }

        R createdRow = repository.save(conversion.to(resource));

        return conversion.from(createdRow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> create(Collection<T> resources) {
        Collection<R> createdRows = repository.save(conversion.to(resources));

        return conversion.from(createdRows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(ID resourceId) {
        if (resourceId == null) {
            return false;
        }

        return repository.exists(resourceId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return repository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(ID resourceId) {
        if (resourceId == null) {
            throw new IllegalArgumentException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        Optional<R> resourceRow = repository.findOne(resourceId);

        R resource = resourceRow.orElseThrow(() -> new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND));

        return conversion.from(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> findAll() {
        return new TreeSet<>(conversion.from(repository.findAll()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> find(Collection<ID> resourcesIds) {
        return new TreeSet<>(conversion.from(repository.find(resourcesIds)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T resource) {
        if (resource == null) {
            throw new IllegalArgumentException();
        }

        if (!repository.exists(resource.id())) {
            throw new NoSuchElementException(MESSAGE_RESOURCE_NOT_FOUND);
        }

        return conversion.from(repository.save(conversion.to(resource)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> update(Collection<T> resources) {
        Collection<R> mappedResources = conversion.to(resources);

        return conversion.from(repository.save(mappedResources));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(ID id) {
        if (id == null) {
            return false;
        }

        return repository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Collection<ID> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return false;
        }

        return repository.delete(resourceIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(T resource) {
        if (resource == null ){
            return false;
        }

        return delete(resource.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {
        return repository.deleteAll();
    }
}
