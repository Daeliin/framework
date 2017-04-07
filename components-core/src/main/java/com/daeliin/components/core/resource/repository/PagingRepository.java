package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type  
 */
public interface PagingRepository<E, ID> {

    E save(E resource);

    Iterable<E> save(Iterable<E> resources);

    E findOne(ID resourceId);

    Iterable<E> findAll(Iterable<ID> resources);

    Page<E> findAll(PageRequest pageRequest);

    Iterable<E> findAll();

    boolean exists(ID resourceId);

    long count();

    E update(E resource);

    void delete(ID resourceId);

    void delete(Iterable<ID> resourceIds);

    void deleteAll();
}
