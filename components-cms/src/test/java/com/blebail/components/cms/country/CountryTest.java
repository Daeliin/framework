package com.blebail.components.cms.country;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CountryTest {

    @Test
    public void shouldThrowException_whenCodeIsNull() {
        assertThrows(Exception.class, () -> new Country(null, Instant.now(), "France"));
    }

    @Test
    public void shouldThrowException_whenNameIsNull() {
        assertThrows(Exception.class, () -> new Country("FR", Instant.now(), null));
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
    public void shouldBeComparedOnName() {
        Country country1 = new Country("BE", Instant.now(), "Belgique");
        Country country2 = new Country("FR", Instant.now(), "France");

        assertThat(country1.compareTo(country2)).isNegative();
        assertThat(country2.compareTo(country1)).isPositive();
    }
}