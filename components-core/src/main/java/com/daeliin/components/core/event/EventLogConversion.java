package com.daeliin.components.core.event;

import com.daeliin.components.core.sql.BEventLog;

public final class EventLogConversion {

    public static EventLog from(BEventLog bEventLog) {
        if (bEventLog == null) {
            return null;
        }

        return new EventLog(
                bEventLog.getId(),
                bEventLog.getUuid(),
                bEventLog.getCreationDate().toLocalDateTime(),
                bEventLog.getDescriptionKey());
    }
}
