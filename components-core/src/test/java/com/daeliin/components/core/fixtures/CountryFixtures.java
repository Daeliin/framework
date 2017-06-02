package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.sql.BCountry;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class CountryFixtures {

    public static BCountry france() {
        return new BCountry(
                "FR",
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "France");
    }

    public static BCountry belgium() {
        return new BCountry(
                "BE",
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "Belgium");
    }
}
