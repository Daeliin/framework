package com.daeliin.components.persistence.event;

import com.daeliin.components.persistence.fixtures.EventLogFixtures;
import com.daeliin.components.persistence.library.EventLogLibrary;
import com.daeliin.components.core.sql.BEventLog;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogConversionTest {

    private EventLogConversion eventLogConversion = new EventLogConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        EventLog nullEventLog = null;

        assertThat(eventLogConversion.map(nullEventLog)).isNull();
    }

    @Test
    public void shouldMapEventLog() {
        BEventLog mappedEventLog = eventLogConversion.map(EventLogLibrary.login());

        assertThat(mappedEventLog).isEqualToComparingFieldByField(EventLogFixtures.login());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        BEventLog nullEventLogRow = null;

        assertThat(eventLogConversion.instantiate(nullEventLogRow)).isNull();
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        EventLog rebuiltEventLog = eventLogConversion.instantiate(EventLogFixtures.login());

        assertThat(rebuiltEventLog).isEqualTo(EventLogLibrary.login());
    }
}