package com.daeliin.components.webservices.library;

import com.daeliin.components.webservices.fake.UuidPersistentResourceDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UuidPersistentResourceDtoLibrary {

    public static UuidPersistentResourceDto uuidPersistentResourceDto1() {
        return new UuidPersistentResourceDto(
                "d5666c5a-df28-49be-b511-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label1");
    }

    public static UuidPersistentResourceDto uuidPersistentResourceDto2() {
        return new UuidPersistentResourceDto(
                "d5666c5a-df28-49be-b512-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label2");
    }

    public static UuidPersistentResourceDto uuidPersistentResourceDto3() {
        return new UuidPersistentResourceDto(
                "d5666c5a-df28-49be-b513-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label3");
    }

    public static UuidPersistentResourceDto uuidPersistentResourceDto4() {
        return new UuidPersistentResourceDto(
                "d5666c5a-df28-49be-b514-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label4");
    }
}
