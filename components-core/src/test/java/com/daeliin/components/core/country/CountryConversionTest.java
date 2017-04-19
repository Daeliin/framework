package com.daeliin.components.core.country;

import com.daeliin.components.core.fixtures.CountryFixtures;
import com.daeliin.components.core.sql.BCountry;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public final class CountryConversionTest {

    private CountryConversion countryConversion = new CountryConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        assertThat(countryConversion.map(null)).isNull();
    }

    @Test
    public void shouldMapCountry() {
        Country country = CountryFixtures.france();
        BCountry mappedCountry = countryConversion.map(country);

        assertThat(mappedCountry.getCreationDate().toLocalDateTime()).isEqualTo(country.creationDate());
        assertThat(mappedCountry.getCode()).isEqualTo(country.code);
        assertThat(mappedCountry.getName()).isEqualTo(country.name);
        assertThat(mappedCountry.getUuid()).isEqualTo(country.id());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(countryConversion.instantiate(null)).isNull();
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        Country country = CountryFixtures.france();
        BCountry mappedCountry = new BCountry(country.code, Timestamp.valueOf(country.creationDate()), country.name, country.id());
        Country rebuiltCountry = countryConversion.instantiate(mappedCountry);

        assertThat(rebuiltCountry).isEqualTo(country);
    }
}