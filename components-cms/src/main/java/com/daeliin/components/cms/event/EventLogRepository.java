package com.daeliin.components.cms.event;

import com.daeliin.components.cms.sql.BEventLog;
import com.daeliin.components.cms.sql.QEventLog;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class EventLogRepository extends ResourceRepository<BEventLog, String> {

    public EventLogRepository() {
        super(QEventLog.eventLog, QEventLog.eventLog.id, BEventLog::getId);
    }
}
