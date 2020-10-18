package com.blebail.components.persistence.resource.service;

import com.blebail.components.persistence.resource.Persistable;
import com.blebail.querydsl.crud.commons.page.Page;
import com.blebail.querydsl.crud.commons.page.PageRequest;
import com.querydsl.core.types.Predicate;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides pagination for a resource.
 * @param <T> resource type
 * @param <ID> resource ID type
 */
public interface PagingService<T extends Persistable<ID>, ID> {

    /**
     * Finds a resource according to a predicate.
     * @param predicate the predicate
     * @return the resource matching the predicate if one was found
     */
    Optional<T> findOne(Predicate predicate);

    /**
     * Finds resources according to a predicate.
     * @param predicate the predicate
     * @return the resources matching the predicate
     */
    Collection<T> find(Predicate predicate);

    /**
     * Finds a page of resources.
     * @param pageRequest the resource page request
     * @return resource page
     */
    Page<T> find(PageRequest pageRequest);

    /**
     * Finds a resource page according to a predicate.
     * @param predicate the predicate
     * @param pageRequest the resource page request
     * @return the resource page matching the predicate
     */
    Page<T> find(Predicate predicate, PageRequest pageRequest);

    /**
     * Returns the total number of resources according to a predicate
     * @param predicate the predicate
     * @return total number of resources matching the predicate
     */
    long count(Predicate predicate);

    /**
     * Delete resources accoding to a predicate
     * @param predicate the predicate
     * @return true if resources were deleted, false otherwise
     */
    boolean delete(Predicate predicate);
}
