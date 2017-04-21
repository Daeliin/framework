package com.daeliin.components.core.resource.repository;

import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;

import javax.inject.Inject;
/**
  * @param <R> row type
  */
public abstract class Repository<R> {

    @Inject
    protected SQLQueryFactory queryFactory;

    protected final RelationalPathBase<R> rowPath;

    public Repository(RelationalPathBase<R> rowPath) {
        this.rowPath = rowPath;
    }
}
