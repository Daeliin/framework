package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;

import java.util.Collection;

/**
 * Provides CRUD operations and pagination for a table.
 * @param <T> row type
 * @param <ID> resource ID
 */
public interface PagingRepository<T, ID> {

    T save(T resource);

    Collection<T> save(Collection<T> resources);

    T findOne(ID resourceId);

    Collection<T> findAll(Collection<ID> resourceIds);

    Page<T> findAll(PageRequest pageRequest);

    Collection<T> findAll();

    boolean exists(ID resourceId);

    long count();

    boolean delete(ID resourceId);

    boolean delete(Collection<ID> resourceIds);

    boolean deleteAll();
}
