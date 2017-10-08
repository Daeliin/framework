package com.daeliin.components.cms.event;

import com.daeliin.components.cms.fixtures.EventLogFixtures;
import com.daeliin.components.cms.library.EventLogLibrary;
import com.daeliin.components.cms.sql.BEventLog;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogConversionTest {

    private EventLogConversion eventLogConversion = new EventLogConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        EventLog nullEventLog = null;

        eventLogConversion.map(nullEventLog);
    }

    @Test
    public void shouldMapEventLog() {
        BEventLog mappedEventLog = eventLogConversion.map(EventLogLibrary.login());

        assertThat(mappedEventLog).isEqualToComparingFieldByField(EventLogFixtures.login());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BEventLog nullEventLogRow = null;

        eventLogConversion.instantiate(nullEventLogRow);
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        EventLog rebuiltEventLog = eventLogConversion.instantiate(EventLogFixtures.login());

        assertThat(rebuiltEventLog).isEqualTo(EventLogLibrary.login());
    }
}