package com.daeliin.components.core.resource.repository;

import com.querydsl.sql.RelationalPathBase;

/**
 * @param <R> row type
 */
public interface Repository<R> {

    RelationalPathBase<R> rowPath();
}
