package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class ResourceRepository<R, ID> extends Repository<R> implements PagingRepository<R, ID> {

    protected final SimpleExpression<ID> idPath;
    protected final Function<R, ID> idMapping;

    public ResourceRepository(RelationalPathBase<R> rowPath, SimpleExpression<ID> idPath, Function<R, ID> idMapping) {
        super(rowPath);
        this.idPath = idPath;
        this.idMapping = idMapping;
    }

    @Transactional
    @Override
    public R save(R resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Cannot create null resource");
        }

        ID resourceId = idMapping.apply(resource);

        if (exists(resourceId)) {
            queryFactory.update(rowPath)
                    .populate(resource)
                    .where(idPath.eq(resourceId))
                    .execute();
        } else {
            queryFactory.insert(rowPath)
                    .populate(resource)
                    .execute();
        }

        return resource;
    }

    @Transactional
    @Override
    public Collection<R> save(Collection<R> resources) {
        Collection<ID> resourceIds = resources.stream().map(idMapping::apply).collect(Collectors.toList());
        Collection<ID> persistedResourceIds = findAllIds(resources);
        boolean insertBatchShouldBeExecuted = resourceIds.size() > persistedResourceIds.size();
        boolean updateBatchShouldBeExecuted = persistedResourceIds.size() > 0;

        SQLInsertClause insertBatch = queryFactory.insert(rowPath);
        SQLUpdateClause updateBatch = queryFactory.update(rowPath);

        resources.forEach(resource -> {
            ID resourceId = idMapping.apply(resource);

            if (persistedResourceIds.contains(resourceId)) {
                updateBatch.populate(resource).addBatch();
            } else {
                insertBatch.populate(resource).addBatch();
            }
        });

        if (insertBatchShouldBeExecuted) {
            insertBatch.execute();
        }

        if (updateBatchShouldBeExecuted) {
            updateBatch.execute();
        }

        return resources;
    }

    @Transactional(readOnly = true)
    @Override
    public R findOne(ID resourceId) {
        if (resourceId == null) {
            return null;
        }

        return queryFactory.select(rowPath)
                .from(rowPath)
                .where(idPath.eq(resourceId))
                .fetchOne();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> findAll(Collection<ID> resourceIds) {
        return queryFactory.select(rowPath)
                .from(rowPath)
                .where(idPath.in(resourceIds))
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
    public boolean exists(ID resourceId) {
        if (resourceId == null) {
            return false;
        }

        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.eq(resourceId))
                .fetchOne() != null;
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return queryFactory.select(idPath)
                .from(rowPath)
                .fetchCount();
    }

    @Transactional
    @Override
    public boolean delete(ID resourceId) {
        if (resourceId == null) {
            return false;
        }

        return queryFactory.delete(rowPath)
                .where(idPath.eq(resourceId))
                .execute() == 1;
    }

    @Transactional
    @Override
    public boolean delete(Collection<ID> resourceIds) {
        return queryFactory.delete(rowPath)
                .where(idPath.in(resourceIds))
                .execute() == resourceIds.size();
    }

    @Transactional
    @Override
    public boolean deleteAll() {
        return queryFactory.delete(rowPath).execute() > 0;
    }

    protected Collection<ID> findAllIds(Collection<R> resources) {
        Collection<ID> resourceIds = resources.stream().map(idMapping::apply).collect(Collectors.toList());

        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.in(resourceIds))
                .fetch();
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
