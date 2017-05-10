package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @param <R> row type
 */
public abstract class RowRepository<R> extends BaseRepository<R> implements TableRepository<R> {

    public RowRepository(RelationalPathBase<R> rowPath) {
        super(rowPath);
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
        long totalPages = computeTotalPages(totalItems, pageRequest.size);
        OrderSpecifier[] orders = computeOrders(pageRequest);

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

    protected int computeTotalPages(long totalItems, long pageSize) {
        return Double.valueOf(Math.ceil((double) totalItems / (double) pageSize)).intValue();
    }

    protected OrderSpecifier[] computeOrders(PageRequest pageRequest) {
        List<OrderSpecifier> orders = computeOrderSpecifiers(getSortablePaths(rowPath), pageRequest);

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }

    private List<Path> getSortablePaths(RelationalPathBase<R> rowPath) {
        return rowPath.getColumns()
                .stream()
                .filter(path -> path instanceof ComparableExpressionBase)
                .collect(toList());
    }

    private List<OrderSpecifier> computeOrderSpecifiers(List<Path> sortablePaths, PageRequest pageRequest) {
        List<OrderSpecifier> orders = new ArrayList<>();

        for (Path<?> path : sortablePaths) {
            String columnName = path.getMetadata().getName();

            if (pageRequest.sorts.containsKey(columnName)) {
                ComparableExpressionBase comparableExpressionBase = (ComparableExpressionBase) path;

                if (pageRequest.sorts.containsKey(columnName)) {
                    switch (pageRequest.sorts.get(columnName)) {
                        case ASC:
                            orders.add(comparableExpressionBase.asc());
                            break;
                        case DESC:
                            orders.add(comparableExpressionBase.desc());
                            break;
                        default:
                            orders.add(comparableExpressionBase.asc());
                            break;
                    }
                }
            }
        }

        return orders;
    }
}
