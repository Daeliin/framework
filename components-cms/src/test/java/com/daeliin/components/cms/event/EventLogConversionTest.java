package com.daeliin.components.cms.event;

import com.daeliin.components.cms.fixtures.EventLogRows;
import com.daeliin.components.cms.library.EventLogLibrary;
import com.daeliin.components.cms.library.PersistenceConversionTest;
import com.daeliin.components.cms.sql.BEventLog;
import com.daeliin.components.core.resource.Conversion;

public final class EventLogConversionTest extends PersistenceConversionTest<EventLog, BEventLog> {

    @Override
    protected Conversion<EventLog, BEventLog> conversion() {
        return new EventLogConversion();
    }

    @Override
    protected EventLog object() {
        return EventLogLibrary.login();
    }

    @Override
    protected BEventLog converted() {
        return EventLogRows.login();
    }
}