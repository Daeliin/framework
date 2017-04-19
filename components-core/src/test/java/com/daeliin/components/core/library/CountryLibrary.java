package com.daeliin.components.core.library;

import com.daeliin.components.core.country.Country;

import java.time.LocalDateTime;

public final class CountryLibrary {

    public static Country france() {
        return new Country(
                "FR",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "France");
    }
}
