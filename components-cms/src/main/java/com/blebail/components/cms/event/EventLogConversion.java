package com.blebail.components.cms.event;

import com.blebail.components.cms.sql.BEventLog;
import com.blebail.components.core.resource.Conversion;

public final class EventLogConversion implements Conversion<EventLog, BEventLog> {

    @Override
    public EventLog from(BEventLog bEventLog) {
        return new EventLog(
                bEventLog.getId(),
                bEventLog.getCreationDate(),
                bEventLog.getDescription());
    }

    @Override
    public BEventLog to(EventLog eventLog) {
        return new BEventLog(
                eventLog.creationDate(),
                eventLog.description,
                eventLog.id());
    }
}
