package com.daeliin.components.core.country;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BCountry;
import com.daeliin.components.core.sql.QCountry;
import org.springframework.stereotype.Component;

@Component
public class CountryRepository extends ResourceRepository<Country, BCountry> {

    public CountryRepository() {
        super(new CountryConversion(), QCountry.country, QCountry.country.uuid);
    }
}
