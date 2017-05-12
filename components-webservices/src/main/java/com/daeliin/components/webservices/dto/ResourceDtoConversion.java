package com.daeliin.components.webservices.dto;

import com.daeliin.components.domain.resource.Persistable;

import java.time.LocalDateTime;
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
     * Instantiates an DTO object from a converted object.
     * @param conversion a converted object
     * @return the new DTO instance
     */
    V instantiate(C conversion);

    /**
     * Maps a DTO object to a converted object.
     * @param object the DTO object to map
     * @param object the DTO id to map
     * @return the converted object
     */
    C map(V object, ID id, LocalDateTime creationDate);

    /**
     * Instantiates a collection of DTO objects from a collection of converted objects.
     * @param conversions the converted objects to instantiate
     * @return the new collection of DTO instance, in the same order as the converted objects
     */
    default Set<V> instantiate(Collection<C> conversions) {
        return conversions
                .stream()
                .map(this::instantiate)
                .collect(toCollection(LinkedHashSet::new));
    }
}
