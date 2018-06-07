package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.predicate.PersistentResourcePredicate;
import com.daeliin.components.persistence.sql.QUuidPersistentResource;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Objects;

public class UuidPersistentResourcePredicate implements PersistentResourcePredicate {

    public final String label;

    public UuidPersistentResourcePredicate(String label) {
        this.label = label;
    }

    @Override
    public BooleanExpression compute() {
        return QUuidPersistentResource.uuidPersistentResource.label.equalsIgnoreCase(label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UuidPersistentResourcePredicate that = (UuidPersistentResourcePredicate) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
