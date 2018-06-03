package com.daeliin.components.cms.country;

import com.daeliin.components.cms.fixtures.CountryRows;
import com.daeliin.components.cms.library.CountryLibrary;
import com.daeliin.components.cms.sql.BCountry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class CountryConversionTest {

    private CountryConversion countryConversion = new CountryConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        Country nullCountry = null;

        countryConversion.to(nullCountry);
    }

    @Test
    public void shouldMapCountry() {
        BCountry mappedCountry = countryConversion.to(CountryLibrary.france());

        assertThat(mappedCountry).isEqualToComparingFieldByField(CountryRows.france());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BCountry nullCountryRow = null;

        countryConversion.from(nullCountryRow);
    }

    @Test
    public void shouldInstantiateAnCountry() {
        Country rebuiltCountry = countryConversion.from(CountryRows.france());

        assertThat(rebuiltCountry).isEqualTo(CountryLibrary.france());
    }
}