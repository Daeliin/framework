package com.daeliin.components.cms.event;

import com.daeliin.components.cms.sql.BEventLog;
import com.daeliin.components.core.resource.Conversion;

public final class EventLogConversion implements Conversion<EventLog, BEventLog> {

    @Override
    public EventLog instantiate(BEventLog bEventLog) {
        return new EventLog(
                bEventLog.getId(),
                bEventLog.getCreationDate(),
                bEventLog.getDescription());
    }

    @Override
    public BEventLog map(EventLog eventLog) {
        return new BEventLog(
                eventLog.getCreationDate(),
                eventLog.description,
                eventLog.getId());
    }
}
