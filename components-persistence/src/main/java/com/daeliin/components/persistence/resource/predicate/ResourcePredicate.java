package com.daeliin.components.persistence.resource.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface ResourcePredicate {

    BooleanExpression compute();
}
