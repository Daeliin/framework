package com.daeliin.components.core.library;

import com.daeliin.components.core.fake.UuidPersistentResource;

import java.time.LocalDateTime;

public final class UuidPersistentResourceLibrary {

    public static UuidPersistentResource uuidPersistentResource1() {
        return new UuidPersistentResource(
                "d5666c5a-df28-49be-b511-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label1");
    }

    public static UuidPersistentResource uuidPersistentResource2() {
        return new UuidPersistentResource(
                "d5666c5a-df28-49be-b512-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label2");
    }

    public static UuidPersistentResource uuidPersistentResource3() {
        return new UuidPersistentResource(
                "d5666c5a-df28-49be-b513-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label3");
    }

    public static UuidPersistentResource uuidPersistentResource4() {
        return new UuidPersistentResource(
                "d5666c5a-df28-49be-b514-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0),
                "label4");
    }
}
