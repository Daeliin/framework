package com.blebail.components.persistence.resource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class Persistables {

    public static <ID, T extends Persistable<ID>> Set<ID> ids(Iterable<T> persistables) {
        return StreamSupport.stream(persistables.spliterator(), false)
                .map(Persistable::id)
                .collect(Collectors.toSet());
    }
}
