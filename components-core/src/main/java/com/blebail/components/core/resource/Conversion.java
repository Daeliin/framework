package com.blebail.components.core.resource;

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
     * Builds an object from a converted object.
     * @param conversion a converted object
     * @return the new instance
     */
    O from(C conversion);

    /**
     * Converts an object to a converted object.
     * @param object the object to to
     * @return the converted object
     */
    C to(O object);

    /**
     * Builds a collection of objects from a collection of converted objects.
     * @param conversions the converted objects to from
     * @return the new collection of instance, in the same order as the converted objects
     */
    default Set<O> from(Collection<C> conversions) {
        return conversions
                .stream()
                .map(this::from)
                .collect(toCollection(LinkedHashSet::new));
    }

    /**
     * Convers a collection of objects to a collection of converted objects.
     * @param objects the objects to to
     * @return the converted objects in the same order as the objects
     */
    default Set<C> to(Collection<O> objects) {
        return objects
                .stream()
                .map(this::to)
                .collect(toCollection(LinkedHashSet::new));
    }
}
