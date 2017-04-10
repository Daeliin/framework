package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.PersistentResource;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 */
public abstract class ResourceRepository<E extends PersistentResource, Q extends RelationalPathBase<E>, P extends SimpleExpression<ID>, ID extends Serializable> implements PagingRepository<E, ID> {

    private final SQLQueryFactory sqlQueryFactory;
    private final Q rowType;
    private final P idType;

    public ResourceRepository(Q rowType, P idType) {
        this.sqlQueryFactory = null;
        this.rowType = rowType;
        this.idType = idType;
    }

    @Override
    public E save(E resource) {
        return null;
    }

    @Override
    public Collection<E> save(Collection<E> resources) {
        return null;
    }

    @Override
    public E findOne(ID resourceId) {
        return sqlQueryFactory
                .select(rowType)
                .from(rowType)
                .where(idType.eq(resourceId))
                .fetchOne();
    }

    @Override
    public Collection<E> findAll(Collection<ID> resourceIds) {
        return sqlQueryFactory
                .select(rowType)
                .from(rowType)
                .where(idType.in(resourceIds))
                .fetch();
    }

    @Override
    public Page<E> findAll(PageRequest pageRequest) {
        List<E> a = sqlQueryFactory
                .select(rowType)
                .from(rowType)
                .offset(pageRequest.offset)
                .limit(pageRequest.size)
                .orderBy()
                .fetch();
    }

    @Override
    public Collection<E> findAll() {
        return sqlQueryFactory
                .select(rowType)
                .from(rowType)
                .fetch();
    }

    @Override
    public boolean exists(ID resourceId) {
        return findOne(resourceId) != null;
    }

    @Override
    public long count() {
        return sqlQueryFactory
                .select(idType)
                .from(rowType)
                .fetchCount();
    }

    @Override
    public E update(E resource) {
        return null;
    }

    @Override
    public boolean delete(ID resourceId) {
        return sqlQueryFactory
                .delete(rowType)
                .where(idType.eq(resourceId))
                .execute() == 1;
    }

    @Override
    public boolean delete(Collection<ID> resourceIds) {
        return sqlQueryFactory
                .delete(rowType)
                .where(idType.in(resourceIds))
                .execute() == resourceIds.size();
    }

    @Override
    public void deleteAll() {
        sqlQueryFactory
                .delete(rowType)
                .execute();
    }
}
