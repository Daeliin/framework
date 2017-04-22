package com.daeliin.components.core.country;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.core.sql.BCountry;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public final class CountryService extends ResourceService<Country, BCountry, String, CountryRepository> {

    @Inject
    public CountryService(CountryRepository repository) {
        super(repository, new CountryConversion());
    }
}
