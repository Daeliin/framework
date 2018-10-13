package com.blebail.components.cms.event;

import com.blebail.components.cms.fixtures.EventLogRows;
import com.blebail.components.cms.library.EventLogLibrary;
import com.blebail.components.cms.library.PersistenceConversionTest;
import com.blebail.components.cms.sql.BEventLog;
import com.blebail.components.core.resource.Conversion;

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