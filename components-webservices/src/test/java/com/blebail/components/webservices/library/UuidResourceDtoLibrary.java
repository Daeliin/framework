package com.blebail.components.webservices.library;

import com.blebail.components.webservices.fake.UuidResourceDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class UuidResourceDtoLibrary {

    public static UuidResourceDto uuidResourceDto1() {
        return new UuidResourceDto(
                "d5666c5a-df28-49be-b511-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label1");
    }

    public static UuidResourceDto uuidResourceDto2() {
        return new UuidResourceDto(
                "d5666c5a-df28-49be-b512-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label2");
    }

    public static UuidResourceDto uuidResourceDto3() {
        return new UuidResourceDto(
                "d5666c5a-df28-49be-b513-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label3");
    }

    public static UuidResourceDto uuidResourceDto4() {
        return new UuidResourceDto(
                "d5666c5a-df28-49be-b514-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "label4");
    }
}
