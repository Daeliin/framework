package com.daeliin.components.cms.country;

import com.daeliin.components.persistence.resource.PersistentResource;
import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public final class CountryTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(Country.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenCodeIsNull() {
        new Country(null, Instant.now(), "France");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenNameIsNull() {
        new Country("FR", Instant.now(), null);
    }

    @Test
    public void shouldAssignACode() {
        Country country = new Country("FR", Instant.now(), "France");

        assertThat(country.code).isEqualTo("FR");
    }

    @Test
    public void shouldAssignName() {
        Country country = new Country("FR", Instant.now(), "France");

        assertThat(country.name).isEqualTo("France");
    }

    @Test
    public void shouldPrintItsCodeAndName() {
        Country country = new Country("FR", Instant.now(), "France");

        assertThat(country.toString()).contains(country.code, country.name);
    }

    @Test
    public void shouldBeComparedOnCode() {
        Country country1 = new Country("BE", Instant.now(), "Belgique");
        Country country2 = new Country("FR", Instant.now(), "France");

        assertThat(country1.compareTo(country2)).isNegative();
        assertThat(country2.compareTo(country1)).isPositive();
    }
}