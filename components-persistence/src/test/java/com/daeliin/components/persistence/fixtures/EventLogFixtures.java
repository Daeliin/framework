package com.daeliin.components.persistence.fixtures;

import com.daeliin.components.core.sql.BEventLog;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class EventLogFixtures {

    public static BEventLog login() {
        return new BEventLog(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "membership.login",
                "d5666c5a-df28-49be-b711-cfd58d0867cf");
    }
}
