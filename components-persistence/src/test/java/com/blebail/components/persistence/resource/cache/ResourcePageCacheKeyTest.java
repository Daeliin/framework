package com.blebail.components.persistence.resource.cache;

import com.blebail.components.persistence.fake.UuidResourcePredicate;
import com.blebail.components.persistence.resource.predicate.PersistentResourcePredicate;
import com.blebail.querydsl.crud.commons.page.PageRequest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

;

public final class ResourcePageCacheKeyTest {

    @Test
    public void shouldNotBeEqual_whenDifferentPageRequests() {
        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(0, 5, List.of()), Set.of());
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, List.of()), Set.of());

        assertThat(resourcePageCacheKey1).isNotEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isNotEqualTo(resourcePageCacheKey2.hashCode());
    }

    @Test
    public void shouldNotBeEqual_whenDifferentPredicates() {
        Set<PersistentResourcePredicate> predicates1 = new HashSet<>();
        predicates1.add(new UuidResourcePredicate("1"));
        Set<PersistentResourcePredicate> predicates2 = new HashSet<>();
        predicates2.add(new UuidResourcePredicate("2"));

        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(1, 5, List.of()), predicates1);
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, List.of()), predicates2);

        assertThat(resourcePageCacheKey1).isNotEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isNotEqualTo(resourcePageCacheKey2.hashCode());
    }

    @Test
    public void shouldBeEqual_whenSamePageRequestAndPredicates() {
        Set<PersistentResourcePredicate> predicates = new HashSet<>();
        predicates.add(new UuidResourcePredicate("1"));

        ResourcePageCacheKey resourcePageCacheKey1 = new ResourcePageCacheKey(new PageRequest(1, 5, List.of()), predicates);
        ResourcePageCacheKey resourcePageCacheKey2 = new ResourcePageCacheKey(new PageRequest(1, 5, List.of()), predicates);

        assertThat(resourcePageCacheKey1).isEqualTo(resourcePageCacheKey2);
        assertThat(resourcePageCacheKey1.hashCode()).isEqualTo(resourcePageCacheKey2.hashCode());
    }
}