package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.sql.RelationalPathBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public final class RowOrder {

    private final RelationalPathBase<?> rowPath;

    public RowOrder(RelationalPathBase<?> rowPath) {
        this.rowPath = rowPath;
    }

    public int computeTotalPages(long totalItems, long pageSize) {
        return Double.valueOf(Math.ceil((double) totalItems / (double) pageSize)).intValue();
    }

    public OrderSpecifier[] computeOrders(PageRequest pageRequest) {
        List<OrderSpecifier> orders = computeOrderSpecifiers(pageRequest);

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }

    public List<Path> getSortablePaths() {
        return rowPath.getColumns()
                .stream()
                .filter(path -> path instanceof ComparableExpressionBase)
                .collect(toList());
    }

    private Map<String, ComparableExpressionBase> getSortablePathByColumnNames() {
        Map<String, ComparableExpressionBase> sortablePathByColumnNames = new HashMap<>();

        for (Path sortablePath : getSortablePaths()) {
            sortablePathByColumnNames.put(sortablePath.getMetadata().getName().toLowerCase(), (ComparableExpressionBase)sortablePath);
        }

        return sortablePathByColumnNames;
    }

    private List<OrderSpecifier> computeOrderSpecifiers(PageRequest pageRequest) {
        List<OrderSpecifier> orders = new ArrayList<>();

        Map<String, ComparableExpressionBase> sortablePathByColumnNames = getSortablePathByColumnNames();

        for (Sort sort: pageRequest.sorts) {
            String sortColumnName = sort.property.toLowerCase();

            if (sortablePathByColumnNames.containsKey(sortColumnName)) {
                ComparableExpressionBase path = sortablePathByColumnNames.get(sortColumnName);

                switch (sort.direction) {
                    case ASC:
                        orders.add(path.asc());
                        break;
                    case DESC:
                        orders.add(path.desc());
                        break;
                    default:
                        orders.add(path.asc());
                        break;
                }
            }
        }

        return orders;
    }
}
