package com.daeliin.components.core.event;

import com.daeliin.components.core.sql.BEventLog;
import com.daeliin.components.domain.resource.Conversion;

import java.sql.Timestamp;

public final class EventLogConversion implements Conversion<EventLog, BEventLog> {

    @Override
    public EventLog instantiate(BEventLog bEventLog) {
        if (bEventLog == null) {
            return null;
        }

        return new EventLog(
                bEventLog.getId(),
                bEventLog.getCreationDate().toLocalDateTime(),
                bEventLog.getDescription());
    }

    @Override
    public BEventLog map(EventLog eventLog) {
        if (eventLog == null) {
            return null;
        }

        return new BEventLog(
                Timestamp.valueOf(eventLog.creationDate()),
                eventLog.description,
                eventLog.id());
    }
}
