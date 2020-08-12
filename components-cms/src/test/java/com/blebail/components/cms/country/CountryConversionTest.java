package com.blebail.components.cms.country;

import com.blebail.components.cms.fixtures.CountryRows;
import com.blebail.components.cms.library.CountryLibrary;
import com.blebail.components.cms.library.PersistenceConversionTest;
import com.blebail.components.cms.sql.BCountry;
import com.blebail.components.core.resource.Conversion;

public final class CountryConversionTest extends PersistenceConversionTest<Country, BCountry> {

    @Override
    protected Conversion<Country, BCountry> conversion() {
        return new CountryConversion();
    }

    @Override
    protected Country object() {
        return CountryLibrary.france();
    }

    @Override
    protected BCountry converted() {
        return CountryRows.france();
    }
}