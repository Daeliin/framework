package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.Predicate;

import java.util.Collection;

/**
 * @param <R> row type
 */
public interface TableRepository<R> extends Repository<R> {

    Collection<R> findAll(Predicate predicate);

    Page<R> findAll(PageRequest pageRequest);

    Page<R> findAll(Predicate predicate, PageRequest pageRequest);

    Collection<R> findAll();

    long count();

    long count(Predicate predicate);

    boolean deleteAll();
}
