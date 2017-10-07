package com.daeliin.components.cms.event;

import com.daeliin.components.core.resource.Conversion;
import com.daeliin.components.cms.sql.BEventLog;

public final class EventLogConversion implements Conversion<EventLog, BEventLog> {

    @Override
    public EventLog instantiate(BEventLog bEventLog) {
        if (bEventLog == null) {
            return null;
        }

        return new EventLog(
                bEventLog.getId(),
                bEventLog.getCreationDate(),
                bEventLog.getDescription());
    }

    @Override
    public BEventLog map(EventLog eventLog) {
        if (eventLog == null) {
            return null;
        }

        return new BEventLog(
                eventLog.getCreationDate(),
                eventLog.description,
                eventLog.getId());
    }
}
