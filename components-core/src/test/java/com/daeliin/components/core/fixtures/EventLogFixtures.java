package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.event.EventLog;

import java.time.LocalDateTime;

public final class EventLogFixtures {

    public static EventLog eventLog1() {
        return new EventLog(
                1L,
                "d5666c5a-df28-49be-b711-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "event.log1");
    }
}
