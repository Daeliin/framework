package com.daeliin.components.cms.country;

import com.daeliin.components.cms.sql.BCountry;
import com.daeliin.components.core.resource.Conversion;

public final class CountryConversion implements Conversion<Country, BCountry> {

    @Override
    public Country instantiate(BCountry bCountry) {
        return new Country(
                bCountry.getCode(),
                bCountry.getCreationDate(),
                bCountry.getName());
    }

    @Override
    public BCountry map(Country country) {
        return new BCountry(
                country.code,
                country.getCreationDate(),
                country.name);
    }
}
