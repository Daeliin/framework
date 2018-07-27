package com.daeliin.components.cms.country;

import com.daeliin.components.cms.fixtures.CountryRows;
import com.daeliin.components.cms.library.CountryLibrary;
import com.daeliin.components.cms.library.PersistentConversionTest;
import com.daeliin.components.cms.sql.BCountry;
import com.daeliin.components.core.resource.Conversion;

public final class CountryConversionTest extends PersistentConversionTest<Country, BCountry> {

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