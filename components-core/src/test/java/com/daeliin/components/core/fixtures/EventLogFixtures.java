package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.sql.BEventLog;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class EventLogFixtures {

    public static BEventLog login() {
        return new BEventLog(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "membership.login",
                "d5666c5a-df28-49be-b711-cfd58d0867cf");
    }
}
