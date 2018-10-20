package com.blebail.components.persistence.resource.service;

/**
 * Provides cache invalidation.
 * @param <ID> the cache key type
 */
public interface CachedService<ID> {

    /**
     * Invalidates the cache.
     */
    void invalidate();
}
