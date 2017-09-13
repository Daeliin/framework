package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.sql.BUuidPersistentResource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UuidPersistentResourceFixtures {

    public static BUuidPersistentResource uuidPersistentResource1() {
        return new BUuidPersistentResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label1",
                "d5666c5a-df28-49be-b511-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource2() {
        return new BUuidPersistentResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label2",
                "d5666c5a-df28-49be-b512-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource3() {
        return new BUuidPersistentResource(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label3",
                "d5666c5a-df28-49be-b513-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource4() {
        return new BUuidPersistentResource(
                LocalDateTime.of(2017, 1, 2, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label4",
                "d5666c5a-df28-49be-b514-cfd58d0867cf");
    }
}
