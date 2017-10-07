package com.daeliin.components.cms.event;

import com.daeliin.components.core.resource.Id;
import com.daeliin.components.cms.sql.BEventLog;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;

@Service
public final class EventLogService extends ResourceService<EventLog, BEventLog, String, EventLogRepository> {

    @Inject
    public EventLogService(EventLogRepository repository) {
        super(repository, new EventLogConversion());
    }

    public EventLog create(String description) {
        EventLog eventLog = new EventLog(new Id().value, Instant.now(), description);
        return super.create(eventLog);
    }
}
