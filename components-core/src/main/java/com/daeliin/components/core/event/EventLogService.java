package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.domain.utils.Id;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Service
public final class EventLogService extends ResourceService<EventLog, BEventLog, String, EventLogRepository> {

    @Inject
    public EventLogService(EventLogRepository repository) {
        super(repository, new EventLogConversion());
    }

    public EventLog create(String description) {
        EventLog eventLog = new EventLog(new Id().value, LocalDateTime.now(), description);
        return super.create(eventLog);
    }
}
