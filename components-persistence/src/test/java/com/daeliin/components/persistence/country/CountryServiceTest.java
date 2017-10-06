package com.daeliin.components.persistence.country;

import com.daeliin.components.persistence.Application;
import com.daeliin.components.persistence.exception.PersistentResourceNotFoundException;
import com.daeliin.components.persistence.library.CountryLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class CountryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private CountryService countryService;

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowException_whenFindingNonExistingCountryCode() {
        countryService.findByCode("nope");
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowException_whenFindingNullCountryCode() {
        String nullCountryCode = null;

        countryService.findByCode(nullCountryCode);
    }

    @Test
    public void shouldFindAllCountries() {
        Collection<Country> allCountries = countryService.findAll();

        assertThat(allCountries).containsOnly(CountryLibrary.france(), CountryLibrary.belgium());
    }

    @Test
    public void shouldFindACountryByCode() {
        Country france = CountryLibrary.france();

        Country foundCountry = countryService.findByCode(france.code);

        assertThat(foundCountry).isEqualTo(france);
    }
}