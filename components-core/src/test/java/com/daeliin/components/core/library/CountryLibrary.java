package com.daeliin.components.core.library;

import com.daeliin.components.core.country.Country;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class CountryLibrary {

    public static Country france() {
        return new Country(
                "FR",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "France");
    }

    public static Country belgium() {
        return new Country(
                "BE",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "Belgium");
    }
}
