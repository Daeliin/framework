package com.blebail.components.cms.event;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class EventLogTest {

    @Test
    public void shouldThrowException_whenDescriptionIsNull() {
        assertThrows(Exception.class, () -> new EventLog(UUID.randomUUID().toString(), Instant.now(), null));
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