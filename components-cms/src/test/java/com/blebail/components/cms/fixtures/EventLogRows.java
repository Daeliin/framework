package com.blebail.components.cms.fixtures;

import com.blebail.components.cms.sql.BEventLog;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class EventLogRows {

    public static BEventLog login() {
        return new BEventLog(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "membership.login",
                "d5666c5a-df28-49be-b711-cfd58d0867cf");
    }
}
