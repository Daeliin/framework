package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.SimpleExpression;

import java.util.Collection;
import java.util.function.Function;

/**
 * Provides CRUD operations and pagination for a table.
 * @param <R> row type
 * @param <ID> resource ID type
 */
public interface PagingRepository<R, ID> extends Repository<R> {

    R save(R resource);

    Collection<R> save(Collection<R> resources);

    R findOne(ID resourceId);

    Collection<R> findAll(Predicate predicate);

    Collection<R> findAll(Collection<ID> resourceIds);

    Page<R> findAll(PageRequest pageRequest);

    Collection<R> findAll();

    boolean exists(ID resourceId);

    long count();

    boolean delete(ID resourceId);

    boolean delete(Collection<ID> resourceIds);

    boolean deleteAll();

    SimpleExpression<ID> idPath();

    Function<R, ID> idMapping();
}
