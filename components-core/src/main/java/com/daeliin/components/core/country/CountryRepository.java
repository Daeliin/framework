package com.daeliin.components.core.country;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BCountry;
import com.daeliin.components.core.sql.QCountry;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

@Component
public final class CountryRepository extends ResourceRepository<Country, BCountry, CountryConversion> {

    @Inject
    public CountryRepository(DataSource dataSource) {
        super(dataSource, new CountryConversion(), QCountry.country, QCountry.country.id);
    }
}
