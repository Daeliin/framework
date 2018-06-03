package com.daeliin.components.cms.event;

import com.daeliin.components.cms.fixtures.EventLogRows;
import com.daeliin.components.cms.library.EventLogLibrary;
import com.daeliin.components.cms.sql.BEventLog;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogConversionTest {

    private EventLogConversion eventLogConversion = new EventLogConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        EventLog nullEventLog = null;

        eventLogConversion.to(nullEventLog);
    }

    @Test
    public void shouldMapEventLog() {
        BEventLog mappedEventLog = eventLogConversion.to(EventLogLibrary.login());

        assertThat(mappedEventLog).isEqualToComparingFieldByField(EventLogRows.login());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BEventLog nullEventLogRow = null;

        eventLogConversion.from(nullEventLogRow);
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        EventLog rebuiltEventLog = eventLogConversion.from(EventLogRows.login());

        assertThat(rebuiltEventLog).isEqualTo(EventLogLibrary.login());
    }
}