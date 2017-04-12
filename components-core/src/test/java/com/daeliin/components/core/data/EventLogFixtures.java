package com.daeliin.components.core.data;

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

    public static EventLog eventLog2() {
        return new EventLog(
                2L,
                "d5666c5a-df28-49be-b712-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "event.log2");
    }

    public static EventLog eventLog3() {
        return new EventLog(
                3L,
                "d5666c5a-df28-49be-b713-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "event.log3");
    }

    public static EventLog eventLog4() {
        return new EventLog(
                4L,
                "d5666c5a-df28-49be-b714-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0),
                "event.log4");
    }
}
