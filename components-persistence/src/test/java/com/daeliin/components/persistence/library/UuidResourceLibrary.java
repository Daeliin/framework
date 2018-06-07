package com.daeliin.components.persistence.library;

import com.daeliin.components.persistence.fake.UuidResource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UuidResourceLibrary {

    public static UuidResource uuidResource1() {
        return new UuidResource(
                "d5666c5a-df28-49be-b511-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label1");
    }

    public static UuidResource uuidResource2() {
        return new UuidResource(
                "d5666c5a-df28-49be-b512-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label2");
    }

    public static UuidResource uuidResource3() {
        return new UuidResource(
                "d5666c5a-df28-49be-b513-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label3");
    }

    public static UuidResource uuidResource4() {
        return new UuidResource(
                "d5666c5a-df28-49be-b514-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label4");
    }
}
