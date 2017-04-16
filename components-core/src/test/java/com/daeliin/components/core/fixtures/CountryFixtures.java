package com.daeliin.components.core.fixtures;

import com.daeliin.components.core.country.Country;

import java.time.LocalDateTime;

public final class CountryFixtures {

    public static Country france() {
        return new Country(
                "d0202c5a-df28-49be-b711-cfd58d0867cf",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "FR",
                "France");
    }
}
