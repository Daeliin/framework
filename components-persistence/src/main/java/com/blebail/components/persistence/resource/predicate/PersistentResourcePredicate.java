package com.blebail.components.persistence.resource.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface PersistentResourcePredicate {

    BooleanExpression compute();
}
