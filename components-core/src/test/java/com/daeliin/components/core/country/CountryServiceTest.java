package com.daeliin.components.core.country;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.library.CountryLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

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
        String nullContryCode = null;

        countryService.findByCode(nullContryCode);
    }

    @Test
    public void shouldFindACountryByCode() {
        Country france = CountryLibrary.france();

        Country foundCountry = countryService.findByCode(france.code);

        assertThat(foundCountry).isEqualTo(france);
    }
}