package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.PagingRepository;
import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.core.sql.QEventLog;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.domain.resource.Persistable;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public final class EventLogRepository implements PagingRepository<EventLog> {

    public final SQLQueryFactory queryFactory;
    private final Conversion<EventLog, BEventLog> conversion;

    @Inject
    public EventLogRepository(DataSource dataSource) {
        this.queryFactory = new SQLQueryFactory(new Configuration(SQLTemplates.DEFAULT), dataSource);
        this.conversion = new EventLogConversion();
    }

    @Override
    public EventLog save(EventLog resource) {
        if (exists(resource.id())) {
            queryFactory.update(QEventLog.eventLog)
                    .populate(conversion.map(resource))
                    .execute();
        } else {
            queryFactory.insert(QEventLog.eventLog)
                    .populate(conversion.map(resource))
                    .execute();
        }

        return resource;
    }

    @Override
    public Collection<EventLog> save(Collection<EventLog> resources) {
        Collection<Long> resourceIds = resources.stream().map(Persistable::id).collect(Collectors.toList());
        Collection<Long> persistedResourceIds = findAllIds(resources);
        boolean insertBatchShouldBeExecuted = resourceIds.size() > persistedResourceIds.size();
        boolean updateBatchShouldBeExecuted = persistedResourceIds.size() > 0;

        SQLInsertClause insertBatch = queryFactory.insert(QEventLog.eventLog).addBatch();
        SQLUpdateClause updateBatch = queryFactory.update(QEventLog.eventLog).addBatch();

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
    public EventLog findOne(Long resourceId) {
        BEventLog persistedEventLog = queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.eq(resourceId))
                .fetchOne();

        return conversion.instantiate(persistedEventLog);
    }

    @Override
    public Collection<EventLog> findAll(Collection<Long> resourceIds) {
        return queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.in(resourceIds))
                .fetch()
                .stream()
                .map(conversion::instantiate)
                .collect(toList());
    }

    @Override
    public Page<EventLog> findAll(PageRequest pageRequest) {
        long totalElements = count();
        long totalPages = Double.valueOf(Math.ceil(totalElements / pageRequest.size)).intValue();

        List<EventLog> collect = queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
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
    public Collection<EventLog> findAll() {
        return queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
                .fetch()
                .stream()
                .map(conversion::instantiate)
                .collect(toList());
    }

    @Override
    public boolean exists(Long resourceId) {
        return queryFactory.select(QEventLog.eventLog.id)
                .from(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.eq(resourceId))
                .fetch() != null;
    }

    @Override
    public long count() {
        return queryFactory.select(QEventLog.eventLog.id)
                .from(QEventLog.eventLog)
                .fetchCount();
    }

    @Override
    public boolean delete(Long resourceId) {
        return queryFactory.delete(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.eq(resourceId))
                .execute() == 1;
    }

    @Override
    public boolean delete(Collection<Long> resourceIds) {
        return queryFactory.delete(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.in(resourceIds))
                .execute() == resourceIds.size();
    }

    @Override
    public boolean deleteAll() {
        return queryFactory.delete(QEventLog.eventLog).execute() > 0;
    }

    private Collection<Long> findAllIds(Collection<EventLog> resources) {
        Collection<Long> resourceIds = resources.stream().map(EventLog::id).collect(Collectors.toList());

        return queryFactory.select(QEventLog.eventLog.id)
                .from(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.in(resourceIds))
                .fetch();
    }

    private OrderSpecifier[] computeOrders(PageRequest pageRequest) {
        List<OrderSpecifier> orders = new ArrayList<>();

        List<Sort> sorts = pageRequest.sorts;
        List<Path> sortablePaths =
                QEventLog.eventLog.getColumns()
                        .stream()
                        .filter(path -> path instanceof ComparableExpressionBase)
                        .collect(toList());

        for (Sort sort : sorts) {
            for (Path<?> path : sortablePaths) {
                String columnName = path.getMetadata().getName();

                if (sort.property.equalsIgnoreCase(columnName)) {
                    ComparableExpressionBase comparableExpressionBase = (ComparableExpressionBase)path;

                    switch(sort.direction) {
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
        }

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }
}
