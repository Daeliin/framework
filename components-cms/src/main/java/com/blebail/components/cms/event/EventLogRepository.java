package com.blebail.components.cms.event;

import com.blebail.components.cms.sql.BEventLog;
import com.blebail.components.cms.sql.QEventLog;
import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class EventLogRepository extends SpringCrudRepository<QEventLog, BEventLog, String> {

    @Inject
    public EventLogRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QEventLog.eventLog, QEventLog.eventLog.id, BEventLog::getId), queryFactory);
    }
}
