package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.core.sql.QEventLog;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

@Component
public final class EventLogRepository extends ResourceRepository<EventLog, BEventLog, EventLogConversion> {

    @Inject
    public EventLogRepository(DataSource dataSource) {
        super(dataSource, new EventLogConversion(), QEventLog.eventLog, QEventLog.eventLog.id);
    }
}
