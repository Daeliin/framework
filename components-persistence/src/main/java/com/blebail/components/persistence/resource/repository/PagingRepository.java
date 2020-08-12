package com.blebail.components.persistence.resource.repository;

import com.blebail.components.core.pagination.Page;
import com.blebail.components.core.pagination.PageRequest;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPathBase;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides global CRUD and pagination operations for a row type.
 * @param <R> row type
 */
public interface PagingRepository<R> {

    RelationalPathBase<R> rowPath();

    Optional<R> findOne(Predicate predicate);

    Collection<R> findAll(Predicate predicate);

    Page<R> findAll(PageRequest pageRequest);

    Page<R> findAll(Predicate predicate, PageRequest pageRequest);

    Collection<R> findAll();

    long count();

    long count(Predicate predicate);

    boolean delete(Predicate predicate);

    boolean deleteAll();
}
