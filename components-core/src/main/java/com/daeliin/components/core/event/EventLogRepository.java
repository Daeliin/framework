package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.core.sql.QEventLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class EventLogRepository extends ResourceRepository<EventLog, BEventLog> {

    public EventLogRepository() {
        super(new EventLogConversion(), QEventLog.eventLog, QEventLog.eventLog.uuid);
    }
}
