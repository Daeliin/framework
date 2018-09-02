package com.daeliin.components.cms.country;

import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.CountryLibrary;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CountryServiceIT {

    @Inject
    private CountryService countryService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.country());

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingNonExistingCountryCode() {
        dbFixture.noRollback();

        countryService.findByCode("nope");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException_whenFindingNullCountryCode() {
        dbFixture.noRollback();

        String nullCountryCode = null;

        countryService.findByCode(nullCountryCode);
    }

    @Test
    public void shouldFindAllCountries() {

        dbFixture.noRollback();
        Collection<Country> allCountries = countryService.findAll();

        assertThat(allCountries).containsOnly(CountryLibrary.france(), CountryLibrary.belgium());
    }

    @Test
    public void shouldFindACountryByCode() {

        dbFixture.noRollback();
        Country france = CountryLibrary.france();

        Country foundCountry = countryService.findByCode(france.code);

        assertThat(foundCountry).isEqualTo(france);
    }
}