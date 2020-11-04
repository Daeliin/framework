package com.blebail.components.cms.country;

import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.cms.library.CountryLibrary;
import com.blebail.junit.SqlFixture;
import com.blebail.junit.SqlMemoryDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CountryServiceIT {

    @Inject
    private CountryService countryService;

    @RegisterExtension
    public static SqlMemoryDb sqlMemoryDb = new SqlMemoryDb();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDb::dataSource, JavaFixtures.country());

    @Test
    public void shouldThrowException_whenFindingNonExistingCountryCode() {
        dbFixture.readOnly();

        assertThrows(NoSuchElementException.class, () -> countryService.findOne("nope"));
    }

    @Test
    public void shouldThrowException_whenFindingNullCountryCode() {
        dbFixture.readOnly();

        assertThrows(IllegalArgumentException.class, () -> countryService.findOne((String) null));
    }

    @Test
    public void shouldFindAllCountries() {

        dbFixture.readOnly();
        Collection<Country> allCountries = countryService.findAll();

        assertThat(allCountries).containsOnly(CountryLibrary.france(), CountryLibrary.belgium());
    }

    @Test
    public void shouldFindACountryByCode() {

        dbFixture.readOnly();
        Country france = CountryLibrary.france();

        Country foundCountry = countryService.findOne(france.code);

        assertThat(foundCountry).isEqualTo(france);
    }
}