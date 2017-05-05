package com.daeliin.components.core.resource.repository;

import com.querydsl.core.types.dsl.SimpleExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * Provides CRUD operations and pagination for a table.
 * @param <R> row type
 * @param <ID> resource ID type
 */
public interface PagingRepository<R, ID> extends TableRepository<R> {

    R save(R resource);

    Collection<R> save(Collection<R> resources);

    R findOne(ID resourceId);

    Collection<R> findAll(Collection<ID> resourceIds);

    boolean exists(ID resourceId);

    boolean delete(ID resourceId);

    boolean delete(Collection<ID> resourceIds);

    SimpleExpression<ID> idPath();

    Function<R, ID> idMapping();
}
