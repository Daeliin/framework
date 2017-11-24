package com.daeliin.components.cms.country;

import com.daeliin.components.cms.Application;
import com.daeliin.components.cms.library.CountryLibrary;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class CountryServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private CountryService countryService;

    @Test
    public void shouldExtendResourceService() {
        assertThat(CountryService.class.getSuperclass().getClass()).isEqualTo(ResourceService.class.getClass());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingNonExistingCountryCode() {
        countryService.findByCode("nope");
    }

    @Test(expected = NoSuchElementException.class)
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