package com.blebail.components.cms.event;

import com.blebail.components.cms.sql.BEventLog;
import com.blebail.components.core.resource.Id;
import com.blebail.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;

@Service
public class EventLogService extends ResourceService<EventLog, BEventLog, String, EventLogRepository> {

    @Inject
    public EventLogService(EventLogRepository repository) {
        super(repository, new EventLogConversion());
    }

    public EventLog create(String description) {
        EventLog eventLog = new EventLog(Id.random(), Instant.now(), description);
        return super.create(eventLog);
    }
}
