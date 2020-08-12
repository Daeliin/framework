package com.blebail.components.cms.event;

import com.blebail.components.cms.sql.BEventLog;
import com.blebail.components.cms.sql.QEventLog;
import com.blebail.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class EventLogRepository extends ResourceRepository<BEventLog, String> {

    public EventLogRepository() {
        super(QEventLog.eventLog, QEventLog.eventLog.id, BEventLog::getId);
    }
}
