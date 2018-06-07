package com.daeliin.components.persistence.resource.cache;

import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.persistence.fake.UuidPersistentResourcePredicate;
import com.daeliin.components.persistence.resource.predicate.PersistentResourcePredicate;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class ResourcePageCacheKeyTest {

    @Test
    public void shouldNotBeEqual_whenDifferentPageRequests() {
        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(0, 5, new LinkedHashSet<>()), Set.of());
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, new LinkedHashSet<>()), Set.of());

        assertThat(resourcePageCacheKey1).isNotEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isNotEqualTo(resourcePageCacheKey2.hashCode());
    }

    @Test
    public void shouldNotBeEqual_whenDifferentPredicates() {
        Set<PersistentResourcePredicate> predicates1 = new HashSet<>();
        predicates1.add(new UuidPersistentResourcePredicate("1"));
        Set<PersistentResourcePredicate> predicates2 = new HashSet<>();
        predicates2.add(new UuidPersistentResourcePredicate("2"));

        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(1, 5, new LinkedHashSet<>()), predicates1);
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, new LinkedHashSet<>()), predicates2);

        assertThat(resourcePageCacheKey1).isNotEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isNotEqualTo(resourcePageCacheKey2.hashCode());
    }

    @Test
    public void shouldBeEqual_whenSamePageRequestAndPredicates() {
        Set<PersistentResourcePredicate> predicates = new HashSet<>();
        predicates.add(new UuidPersistentResourcePredicate("1"));

        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(1, 5, new LinkedHashSet<>()), predicates);
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, new LinkedHashSet<>()), predicates);

        assertThat(resourcePageCacheKey1).isEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isEqualTo(resourcePageCacheKey2.hashCode());
    }
}