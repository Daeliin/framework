package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.fake.UUIDEntity;

import java.time.LocalDateTime;

public final class UUIDEntityFixtures {

    public static UUIDEntity uuidEntity1() {
        return new UUIDEntity(
                1L,
                "d5666c5a-df28-49be-b511-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label1");
    }

    public static UUIDEntity uuidEntity2() {
        return new UUIDEntity(
                2L,
                "d5666c5a-df28-49be-b512-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label2");
    }

    public static UUIDEntity uuidEntity3() {
        return new UUIDEntity(
                3L,
                "d5666c5a-df28-49be-b513-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "label3");
    }

    public static UUIDEntity uuidEntity4() {
        return new UUIDEntity(
                4L,
                "d5666c5a-df28-49be-b514-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 2, 12, 0, 0),
                "label4");
    }
}
