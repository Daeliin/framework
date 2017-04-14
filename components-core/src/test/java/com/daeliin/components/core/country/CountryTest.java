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
        new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), null, "France");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenNameIsNull() {
        new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), "FR", null);
    }

    @Test
    public void shouldAssignACode() {
        Country country = new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), "FR", "France");

        assertThat(country.code).isEqualTo("FR");
    }

    @Test
    public void shouldAssignName() {
        Country country = new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), "FR", "France");

        assertThat(country.name).isEqualTo("France");
    }

    @Test
    public void shouldPrintItsCodeAndName() {
        Country country = new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), "FR", "France");

        assertThat(country.toString()).contains(country.code, country.name);
    }

    @Test
    public void shouldBeComparedOnCode() {
        Country country1 = new Country(1L, UUID.randomUUID().toString(), LocalDateTime.now(), "BE", "Belgique");
        Country country2 = new Country(2L, UUID.randomUUID().toString(), LocalDateTime.now(), "FR", "France");

        assertThat(country1.compareTo(country2)).isNegative();
        assertThat(country2.compareTo(country1)).isPositive();
    }
}