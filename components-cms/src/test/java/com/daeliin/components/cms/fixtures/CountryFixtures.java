package com.daeliin.components.cms.fixtures;

import com.daeliin.components.cms.sql.BCountry;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class CountryFixtures {

    public static BCountry france() {
        return new BCountry(
                "FR",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "France");
    }

    public static BCountry belgium() {
        return new BCountry(
                "BE",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "Belgium");
    }
}
