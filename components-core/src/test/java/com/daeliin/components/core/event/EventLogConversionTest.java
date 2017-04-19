package com.daeliin.components.core.event;

import com.daeliin.components.core.fixtures.EventLogFixtures;
import com.daeliin.components.core.sql.BEventLog;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public final class EventLogConversionTest {

    private EventLogConversion eventLogConversion = new EventLogConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        assertThat(eventLogConversion.map(null)).isNull();
    }

    @Test
    public void shouldMapEventLog() {
        EventLog eventLog = EventLogFixtures.login();
        BEventLog mappedEventLog = eventLogConversion.map(eventLog);

        assertThat(mappedEventLog.getCreationDate().toLocalDateTime()).isEqualTo(eventLog.creationDate());
        assertThat(mappedEventLog.getDescriptionKey()).isEqualTo(eventLog.descriptionKey);
        assertThat(mappedEventLog.getUuid()).isEqualTo(eventLog.id());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(eventLogConversion.instantiate(null)).isNull();
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        EventLog eventLog = EventLogFixtures.login();
        BEventLog mappedEventLog = new BEventLog(Timestamp.valueOf(eventLog.creationDate()), eventLog.descriptionKey, eventLog.id());
        EventLog rebuiltEventLog = eventLogConversion.instantiate(mappedEventLog);

        assertThat(rebuiltEventLog).isEqualTo(eventLog);
    }
}