package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.PersistentResource;

import java.io.Serializable;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 */
public final class ResourceRepository<E extends PersistentResource, ID extends Serializable> implements PagingRepository<E, ID> {

    @Override
    public E save(E resource) {
        return null;
    }

    @Override
    public Iterable<E> save(Iterable<E> resources) {
        return null;
    }

    @Override
    public E findOne(ID resourceId) {
        return null;
    }

    @Override
    public Iterable<E> findAll(Iterable<ID> resources) {
        return null;
    }

    @Override
    public Page<E> findAll(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return null;
    }

    @Override
    public boolean exists(ID resourceId) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public E update(E resource) {
        return null;
    }

    @Override
    public void delete(ID resourceId) {

    }

    @Override
    public void delete(Iterable<ID> resourceIds) {

    }

    @Override
    public void deleteAll() {

    }
}
