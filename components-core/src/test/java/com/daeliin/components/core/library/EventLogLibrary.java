package com.daeliin.components.core.library;

import com.daeliin.components.core.event.EventLog;

import java.time.LocalDateTime;

public final class EventLogLibrary {

    public static EventLog login() {
        return new EventLog(
                "d5666c5a-df28-49be-b711-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "membership.login");
    }
}
