package com.blebail.components.cms.country;

import com.blebail.components.cms.sql.BCountry;
import com.blebail.components.core.resource.Conversion;

public final class CountryConversion implements Conversion<Country, BCountry> {

    @Override
    public Country from(BCountry bCountry) {
        return new Country(
                bCountry.getCode(),
                bCountry.getCreationDate(),
                bCountry.getName());
    }

    @Override
    public BCountry to(Country country) {
        return new BCountry(
                country.code,
                country.creationDate(),
                country.name);
    }
}
