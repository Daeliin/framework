package com.daeliin.components.persistence.event;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.core.sql.QEventLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class EventLogRepository extends ResourceRepository<BEventLog, String> {

    public EventLogRepository() {
        super(QEventLog.eventLog, QEventLog.eventLog.id, BEventLog::getId);
    }
}
