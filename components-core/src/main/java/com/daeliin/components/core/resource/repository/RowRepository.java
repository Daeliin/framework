package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @param <R> row type
 */
public abstract class RowRepository<R> extends BaseRepository<R> implements TableRepository<R> {

    protected RowOrder rowOrder;

    public RowRepository(RelationalPathBase<R> rowPath) {
        super(rowPath);
        rowOrder = new RowOrder(rowPath);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> findAll(Predicate predicate) {
        SQLQuery<R> query = queryFactory.select(rowPath)
                .from(rowPath);

        if (predicate != null) {
            query = query.where(predicate);
        }

        return query.fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<R> findAll(PageRequest pageRequest) {
        return findAll(null, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<R> findAll(Predicate predicate, PageRequest pageRequest) {
        long totalItems = count();
        long totalPages = rowOrder.computeTotalPages(totalItems, pageRequest.size);
        OrderSpecifier[] orders = rowOrder.computeOrders(pageRequest);

        SQLQuery<R> query = queryFactory.select(rowPath)
                .from(rowPath)
                .limit(pageRequest.size)
                .offset(pageRequest.offset)
                .orderBy(orders);

        if (predicate != null) {
            query = query.where(predicate);
        }

        return new Page(query.fetch(), totalItems, totalPages);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> findAll() {
        return queryFactory.select(rowPath)
                .from(rowPath)
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return queryFactory.select(rowPath)
                .from(rowPath)
                .fetchCount();
    }

    @Transactional
    @Override
    public boolean deleteAll() {
        return queryFactory.delete(rowPath).execute() > 0;
    }
}
