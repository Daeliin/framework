package com.daeliin.components.cms.library;

import com.daeliin.components.cms.event.EventLog;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class EventLogLibrary {

    public static EventLog login() {
        return new EventLog(
                "d5666c5a-df28-49be-b711-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "membership.login");
    }
}
