package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPathBase;

import java.util.Collection;

/**
 * @param <R> row type
 */
public interface Repository<R> {

    RelationalPathBase<R> rowPath();

    Collection<R> findAll(Predicate predicate);

    Page<R> findAll(PageRequest pageRequest);

    Collection<R> findAll();

    long count();

    boolean deleteAll();
}
