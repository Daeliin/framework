package com.daeliin.components.core.event;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.core.sql.BEventLog;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public final class EventLogService extends ResourceService<EventLog, BEventLog, String> {

    @Inject
    public EventLogService(EventLogRepository repository) {
        super(repository, new EventLogConversion());
    }
}
