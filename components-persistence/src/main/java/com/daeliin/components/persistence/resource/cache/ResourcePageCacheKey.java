package com.daeliin.components.persistence.resource.cache;

import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.persistence.resource.predicate.PersistentResourcePredicate;

import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class ResourcePageCacheKey {

    public final PageRequest pageRequest;
    public final Set<PersistentResourcePredicate> predicates;

    public ResourcePageCacheKey(PageRequest pageRequest, Set<PersistentResourcePredicate> predicates) {
        this.pageRequest = requireNonNull(pageRequest);
        this.predicates = requireNonNull(predicates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePageCacheKey that = (ResourcePageCacheKey) o;
        return Objects.equals(pageRequest, that.pageRequest) &&
            Objects.equals(predicates, that.predicates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, predicates);
    }
}
