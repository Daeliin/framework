package com.daeliin.components.persistence.resource.repository;

import com.querydsl.core.types.dsl.SimpleExpression;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Provides CRUD for a row type.
 * @param <R> row type
 * @param <ID> resource ID type
 */
public interface CrudRepository<R, ID> extends PagingRepository<R> {

    R save(R resource);

    Collection<R> save(Collection<R> resources);

    Optional<R> findOne(ID resourceId);

    Collection<R> findAll(Collection<ID> resourceIds);

    boolean exists(ID resourceId);

    boolean delete(ID resourceId);

    boolean delete(Collection<ID> resourceIds);

    SimpleExpression<ID> idPath();

    Function<R, ID> idMapping();
}
