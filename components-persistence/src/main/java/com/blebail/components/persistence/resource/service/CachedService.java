package com.blebail.components.persistence.resource.service;

/**
 * Provides cache invalidation.
 */
public interface CachedService {

    /**
     * Invalidates the cache.
     */
    void invalidate();
}
