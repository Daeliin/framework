package com.daeliin.components.core.country;

import com.daeliin.components.core.sql.BCountry;
import com.daeliin.components.domain.resource.Conversion;

public final class CountryConversion implements Conversion<Country, BCountry> {

    @Override
    public Country instantiate(BCountry bCountry) {
        if (bCountry == null) {
            return null;
        }

        return new Country(
                bCountry.getCode(),
                bCountry.getCreationDate(),
                bCountry.getName());
    }

    @Override
    public BCountry map(Country country) {
        if (country == null) {
            return null;
        }

        return new BCountry(
                country.code,
                country.getCreationDate(),
                country.name);
    }
}
