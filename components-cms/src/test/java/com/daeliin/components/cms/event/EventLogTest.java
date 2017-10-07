package com.daeliin.components.cms.event;

import com.daeliin.components.core.resource.PersistentResource;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

public final class EventLogTest {

    @Test
    public void shouldExtendPersistentResource() {
        Assertions.assertThat(EventLog.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenDescriptionIsNull() {
        new EventLog(UUID.randomUUID().toString(), Instant.now(), null);
    }

    @Test
    public void shouldAssignADescription() {
        EventLog eventLog = new EventLog(UUID.randomUUID().toString(), Instant.now(), "description");

        assertThat(eventLog.description).isEqualTo("description");
    }

    @Test
    public void shouldPrintsItsDescription() {
        EventLog eventLog = new EventLog(UUID.randomUUID().toString(), Instant.now(), "description");

        assertThat(eventLog.toString()).contains(String.valueOf(eventLog.description));
    }

    @Test
    public void shouldBeComparedOnCreationDate() {
        Instant creationDate1 = Instant.now();
        Instant creationDate2 = Instant.now().plus(10, ChronoUnit.SECONDS);

        EventLog eventLog1 = new EventLog(UUID.randomUUID().toString(), creationDate1, "description");
        EventLog eventLog2 = new EventLog(UUID.randomUUID().toString(), creationDate2, "description");

        assertThat(eventLog1.compareTo(eventLog2)).isNegative();
        assertThat(eventLog2.compareTo(eventLog1)).isPositive();
    }
}