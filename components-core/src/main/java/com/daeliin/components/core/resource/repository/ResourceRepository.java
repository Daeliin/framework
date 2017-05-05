package com.daeliin.components.core.resource.repository;

import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ResourceRepository<R, ID> extends RowRepository<R> implements TableRepository<R, ID> {

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
    public boolean exists(ID resourceId) {
        if (resourceId == null) {
            return false;
        }

        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.eq(resourceId))
                .fetchOne() != null;
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

    @Override
    public SimpleExpression<ID> idPath(){
        return idPath;
    }

    @Override
    public Function<R, ID> idMapping() {
        return idMapping;
    }

    protected Collection<ID> findAllIds(Collection<R> resources) {
        Collection<ID> resourceIds = resources.stream().map(idMapping::apply).collect(Collectors.toList());

        return queryFactory.select(idPath)
                .from(rowPath)
                .where(idPath.in(resourceIds))
                .fetch();
    }
}
