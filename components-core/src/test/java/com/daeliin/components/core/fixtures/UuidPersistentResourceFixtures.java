package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.sql.BUuidPersistentResource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class UuidPersistentResourceFixtures {

    public static BUuidPersistentResource uuidPersistentResource1() {
        return new BUuidPersistentResource(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "label1",
                "d5666c5a-df28-49be-b511-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource2() {
        return new BUuidPersistentResource(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "label2",
                "d5666c5a-df28-49be-b512-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource3() {
        return new BUuidPersistentResource(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "label3",
                "d5666c5a-df28-49be-b513-cfd58d0867cf");
    }

    public static BUuidPersistentResource uuidPersistentResource4() {
        return new BUuidPersistentResource(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 2, 12, 0, 0)),
                "label4",
                "d5666c5a-df28-49be-b514-cfd58d0867cf");
    }
}
