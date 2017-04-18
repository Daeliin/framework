package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Transactional
public abstract class BaseRepository<B> {

    @Inject
    protected SQLQueryFactory queryFactory;

    protected final RelationalPathBase<B> rowPath;
    protected final StringPath idPath;

    public BaseRepository(RelationalPathBase<B> rowPath, StringPath idPath) {
        this.rowPath = rowPath;
        this.idPath = idPath;
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
