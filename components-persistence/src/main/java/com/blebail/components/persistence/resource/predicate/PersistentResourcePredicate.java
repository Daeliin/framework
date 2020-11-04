package com.blebail.components.persistence.resource.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface PersistentResourcePredicate {

    BooleanExpression compute();

    default BooleanExpression and(BooleanExpression left, BooleanExpression right) {
        return left == null ?
                right :
                left.and(right);
    }
}
