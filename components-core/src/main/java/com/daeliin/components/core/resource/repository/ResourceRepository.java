package com.daeliin.components.core.resource.repository;

import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.domain.resource.Persistable;
import com.daeliin.components.domain.resource.PersistentResource;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class ResourceRepository<E extends PersistentResource, B,  C extends Conversion<E, B>> implements PagingRepository<E> {

    protected final SQLQueryFactory queryFactory;
    protected final Conversion<E, B> conversion;
    protected final RelationalPathBase<B> rowPath;
    protected final NumberPath<Long> idPath;

    protected ResourceRepository(DataSource dataSource, Conversion<E, B> conversion, RelationalPathBase<B> rowPath, NumberPath<Long> idPath) {
        this.queryFactory = new SQLQueryFactory(new Configuration(SQLTemplates.DEFAULT), dataSource);
        this.conversion = conversion;
        this.rowPath = rowPath;
        this.idPath = idPath;
    }

    @Override
    public E save(E resource) {
        if (exists(resource.id())) {
            queryFactory.update(rowPath)
                    .populate(conversion.map(resource))
                    .execute();
        } else {
            queryFactory.insert(rowPath)
                    .populate(conversion.map(resource))
                    .execute();
        }

        return resource;
    }

    @Override
    public Collection<E> save(Collection<E> resources) {
        Collection<Long> resourceIds = resources.stream().map(Persistable::id).collect(Collectors.toList());
        Collection<Long> persistedResourceIds = findAllIds(resources);
        boolean insertBatchShouldBeExecuted = resourceIds.size() > persistedResourceIds.size();
        boolean updateBatchShouldBeExecuted = persistedResourceIds.size() > 0;

        SQLInsertClause insertBatch = queryFactory.insert(rowPath).addBatch();
        SQLUpdateClause updateBatch = queryFactory.update(rowPath).addBatch();

        resources.forEach(eventLog -> {
            if (persistedResourceIds.contains(eventLog.id())) {
                updateBatch.populate(conversion.map(eventLog));
            } else {
                insertBatch.populate(conversion.map(eventLog));
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

    @Override
    public E findOne(Long resourceId) {
        B persistedResource = queryFactory.select(rowPath)
                .from(rowPath)
                .where(idPath.eq(resourceId))
                .fetchOne();

        return conversion.instantiate(persistedResource);
    }

    @Override
    public Collection<E> findAll(Collection<Long> resourceIds) {
        return queryFactory.select(rowPath)
                .from(rowPath)
                .where(idPath.in(resourceIds))
                .fetch()
                .stream()
                .map(conversion::instantiate)
                .collect(toList());
    }

    @Override
    public Page<E> findAll(PageRequest pageRequest) {
        long totalElements = count();
        long totalPages = Double.valueOf(Math.ceil(totalElements / pageRequest.size)).intValue();

        List<E> collect = queryFactory.select(rowPath)
                .from(rowPath)
                .limit(pageRequest.size)
                .offset(pageRequest.offset)
                .orderBy(computeOrders(pageRequest))
                .fetch()
                .stream()
                .map(conversion::instantiate)
                .collect(toList());

        return new Page(collect, totalElements, totalPages);
    }

    @Override
    public Collection<E> findAll() {
        return queryFactory.select(rowPath)
                .from(rowPath)
                .fetch()
                .stream()
                .map(conversion::instantiate)
                .collect(toList());
    }

    @Override
    public boolean exists(Long resourceId) {
        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.eq(resourceId))
                .fetch() != null;
    }

    @Override
    public long count() {
        return queryFactory.select(idPath)
                .from(rowPath)
                .fetchCount();
    }

    @Override
    public boolean delete(Long resourceId) {
        return queryFactory.delete(rowPath)
                .where(idPath.eq(resourceId))
                .execute() == 1;
    }

    @Override
    public boolean delete(Collection<Long> resourceIds) {
        return queryFactory.delete(rowPath)
                .where(idPath.in(resourceIds))
                .execute() == resourceIds.size();
    }

    @Override
    public boolean deleteAll() {
        return queryFactory.delete(rowPath).execute() > 0;
    }

    protected Collection<Long> findAllIds(Collection<E> resources) {
        Collection<Long> resourceIds = resources.stream().map(E::id).collect(Collectors.toList());

        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.in(resourceIds))
                .fetch();
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
                Sort.Direction direction = pageRequest.sorts.get(columnName);

                switch(direction) {
                    case ASC: orders.add(comparableExpressionBase.asc());
                        break;
                    case DESC: orders.add(comparableExpressionBase.desc());
                        break;
                    default: orders.add(comparableExpressionBase.asc());
                        break;
                }

                break;
            }
        }

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }
}
