package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.sql.RelationalPathBase;
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
        if (predicate == null) {
            return new ArrayList<>();
        }

        return queryFactory.select(rowPath)
                .from(rowPath)
                .where(predicate)
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<R> findAll(PageRequest pageRequest) {
        long totalItems = count();
        long totalPages = computeTotalPages(totalItems, pageRequest.size);
        OrderSpecifier[] orders = computeOrders(pageRequest);

        List<R> items = queryFactory.select(rowPath)
                .from(rowPath)
                .limit(pageRequest.size)
                .offset(pageRequest.offset)
                .orderBy(orders)
                .fetch();

        return new Page(items, totalItems, totalPages);
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
        return Double.valueOf(Math.ceil(totalItems / pageSize)).intValue();
    }

    protected OrderSpecifier[] computeOrders(PageRequest pageRequest) {
        List<OrderSpecifier> orders = new ArrayList<>();

        List<Path> sortablePaths =
                rowPath.getColumns()
                        .stream()
                        .filter(path -> path instanceof ComparableExpressionBase)
                        .collect(toList());

        for (Path<?> path : sortablePaths) {
            String columnName = path.getMetadata().getName();

            if (pageRequest.sorts.containsKey(columnName)) {
                ComparableExpressionBase comparableExpressionBase = (ComparableExpressionBase)path;

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

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }
}
