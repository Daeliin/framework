package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;

import java.util.Collection;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 */
public interface PagingRepository<E> {

    E save(E resource);

    Collection<E> save(Collection<E> resources);

    E findOne(String resourceId);

    Collection<E> findAll(Collection<String> resourceIds);

    Page<E> findAll(PageRequest pageRequest);

    Collection<E> findAll();

    boolean exists(String resourceId);

    long count();

    boolean delete(String resourceId);

    boolean delete(Collection<String> resourceIds);

    boolean deleteAll();
}
