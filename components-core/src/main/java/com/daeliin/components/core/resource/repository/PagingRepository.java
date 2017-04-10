package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;

import java.util.Collection;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 */
public interface PagingRepository<E, ID> {

    E save(E resource);

    Collection<E> save(Collection<E> resources);

    E findOne(ID resourceId);

    Collection<E> findAll(Collection<ID> resourceIds);

    Page<E> findAll(PageRequest pageRequest);

    Collection<E> findAll();

    boolean exists(ID resourceId);

    long count();

    E update(E resource);

    boolean delete(ID resourceId);

    boolean delete(Collection<ID> resourceIds);

    void deleteAll();
}
