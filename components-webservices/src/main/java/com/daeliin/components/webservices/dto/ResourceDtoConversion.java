package com.daeliin.components.webservices.dto;

import com.daeliin.components.persistence.resource.Persistable;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

/**
 * Conversion from DTO type V to type C and from type C to DTO type O.
 * @param <V> DTO type
 * @param <C> other type
 * @param <ID> DTO id type
 */
public interface ResourceDtoConversion<V, C extends Persistable<ID>, ID> {

    /**
     * Builds a DTO object from a converted object.
     * @param conversion a converted object
     * @return the new DTO instance
     */
    V from(C conversion);

    /**
     * Converts a DTO object to a converted object.
     * @param object the DTO object to to
     * @param object the DTO id to to
     * @return the converted object
     */
    C to(V object, ID id, Instant creationDate);

    /**
     * Builds a collection of DTO objects from a collection of converted objects.
     * @param conversions the converted objects to from
     * @return the new collection of DTO instance, in the same order as the converted objects
     */
    default Set<V> from(Collection<C> conversions) {
        return conversions
                .stream()
                .map(this::from)
                .collect(toCollection(LinkedHashSet::new));
    }
}
