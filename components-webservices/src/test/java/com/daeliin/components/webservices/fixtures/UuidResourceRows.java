package com.daeliin.components.webservices.fixtures;

import com.daeliin.components.webservices.sql.BUuidResource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UuidResourceRows {

    public static BUuidResource uuidResource1() {
        return new BUuidResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label1",
                "d5666c5a-df28-49be-b511-cfd58d0867cf");
    }

    public static BUuidResource uuidResource2() {
        return new BUuidResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label2",
                "d5666c5a-df28-49be-b512-cfd58d0867cf");
    }

    public static BUuidResource uuidResource3() {
        return new BUuidResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label3",
                "d5666c5a-df28-49be-b513-cfd58d0867cf");
    }

    public static BUuidResource uuidResource4() {
        return new BUuidResource(
                LocalDateTime.of(2017, 1, 2, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label4",
                "d5666c5a-df28-49be-b514-cfd58d0867cf");
    }
}
