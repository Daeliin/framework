package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.PagingRepository;
import com.daeliin.components.core.sql.QEventLog;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.PersistentResource;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public final class EventLogRepository implements PagingRepository<EventLog, Long> {

    public final SQLQueryFactory queryFactory;

    @Inject
    public EventLogRepository(DataSource dataSource) {
        this.queryFactory = new SQLQueryFactory(new Configuration(SQLTemplates.DEFAULT), dataSource);
    }

    @Override
    public EventLog save(EventLog resource) {
        if (exists(resource.id())) {
            queryFactory.update(QEventLog.eventLog)
                    .populate(resource)
                    .execute();
        } else {
            queryFactory.insert(QEventLog.eventLog)
                    .populate(resource)
                    .execute();
        }

        return resource;
    }

    @Override
    public Collection<EventLog> save(Collection<EventLog> resources) {
        Collection<Long> resourceIds = resources.stream().map(PersistentResource::id).collect(Collectors.toList());
        Collection<Long> persistedResourceIds = findAllIds(resources);
        boolean insertBatchShouldBeExecuted = resourceIds.size() > persistedResourceIds.size();
        boolean updateBatchShouldBeExecuted = persistedResourceIds.size() > 0;

        SQLInsertClause insertBatch = queryFactory.insert(QEventLog.eventLog).addBatch();
        SQLUpdateClause updateBatch = queryFactory.update(QEventLog.eventLog).addBatch();

        resources.forEach(eventLog -> {
            if (persistedResourceIds.contains(eventLog.id())) {
                updateBatch.populate(eventLog);
            } else {
                insertBatch.populate(eventLog);
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
        return EventLogConversion.from(
                queryFactory.select(QEventLog.eventLog)
                        .from(QEventLog.eventLog)
                        .where(QEventLog.eventLog.id.eq(resourceId))
                        .fetchOne());
    }

    @Override
    public Collection<EventLog> findAll(Collection<Long> resourceIds) {
        return queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
                .where(QEventLog.eventLog.id.in(resourceIds))
                .fetch()
                .stream()
                .map(EventLogConversion::from)
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
                .fetch()
                .stream()
                .map(EventLogConversion::from)
                .collect(toList());

        return new Page(collect, totalElements, totalPages);
    }

    @Override
    public Collection<EventLog> findAll() {
        return queryFactory.select(QEventLog.eventLog)
                .from(QEventLog.eventLog)
                .fetch()
                .stream()
                .map(EventLogConversion::from)
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
}
