package com.daeliin.components.core.country;

import com.daeliin.components.domain.resource.PersistentResource;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public final class CountryTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(Country.class.getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenCodeIsNull() {
        new Country(null, LocalDateTime.now(), "France");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenNameIsNull() {
        new Country("FR", LocalDateTime.now(), null);
    }

    @Test
    public void shouldAssignACode() {
        Country country = new Country("FR", LocalDateTime.now(), "France");

        assertThat(country.code).isEqualTo("FR");
    }

    @Test
    public void shouldAssignName() {
        Country country = new Country("FR", LocalDateTime.now(), "France");

        assertThat(country.name).isEqualTo("France");
    }

    @Test
    public void shouldPrintItsCodeAndName() {
        Country country = new Country("FR", LocalDateTime.now(), "France");

        assertThat(country.toString()).contains(country.code, country.name);
    }

    @Test
    public void shouldBeComparedOnCode() {
        Country country1 = new Country("BE", LocalDateTime.now(), "Belgique");
        Country country2 = new Country("FR", LocalDateTime.now(), "France");

        assertThat(country1.compareTo(country2)).isNegative();
        assertThat(country2.compareTo(country1)).isPositive();
    }
}