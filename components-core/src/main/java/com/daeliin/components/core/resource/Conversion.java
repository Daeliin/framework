package com.daeliin.components.core.resource;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

/**
 * Conversion from type O to type C and from type C to type O.
 * @param <O> type 1
 * @param <C> type 2
 */
public interface Conversion<O, C> {

    /**
     * Instantiates an object from a converted object.
     * @param conversion a converted object
     * @return the new instance
     */
    O instantiate(C conversion);

    /**
     * Maps an object to a converted object.
     * @param object the object to map
     * @return the converted object
     */
    C map(O object);

    /**
     * Instantiates a collection of objects from a collection of converted objects.
     * @param conversions the converted objects to instantiate
     * @return the new collection of instance, in the same order as the converted objects
     */
    default Set<O> instantiate(Collection<C> conversions) {
        return conversions
                .stream()
                .map(this::instantiate)
                .collect(toCollection(LinkedHashSet::new));
    }

    /**
     * Maps a collection of objects to a collection of converted objects.
     * @param objects the objects to map
     * @return the converted objects in the same order as the objects
     */
    default Set<C> map(Collection<O> objects) {
        return objects
                .stream()
                .map(this::map)
                .collect(toCollection(LinkedHashSet::new));
    }
}
