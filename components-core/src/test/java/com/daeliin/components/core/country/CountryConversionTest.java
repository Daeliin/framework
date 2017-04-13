package com.daeliin.components.core.country;

import com.daeliin.components.core.data.CountryFixtures;
import com.daeliin.components.core.data.EventLogFixtures;
import com.daeliin.components.core.event.EventLog;
import com.daeliin.components.core.sql.BCountry;
import com.daeliin.components.core.sql.BEventLog;
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
        Country country = CountryFixtures.countryFrance();
        BCountry mappedCountry = countryConversion.map(country);

        assertThat(mappedCountry.getCreationDate().toLocalDateTime()).isEqualTo(country.creationDate());
        assertThat(mappedCountry.getCode()).isEqualTo(country.code);
        assertThat(mappedCountry.getName()).isEqualTo(country.name);
        assertThat(mappedCountry.getId()).isEqualTo(country.id());
        assertThat(mappedCountry.getUuid()).isEqualTo(country.uuid());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(countryConversion.instantiate(null)).isNull();
    }

    @Test
    public void shouldInstantiateAnEventLog() {
        Country country = CountryFixtures.countryFrance();
        BCountry mappedCountry = new BCountry(country.code, Timestamp.valueOf(country.creationDate()), country.id(), country.name, country.uuid());
        Country rebuiltCountry = countryConversion.instantiate(mappedCountry);

        assertThat(rebuiltCountry).isEqualTo(country);
    }
}