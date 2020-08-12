package com.blebail.components.cms.country;

import com.blebail.components.cms.sql.BCountry;
import com.blebail.components.persistence.resource.service.CachedBaseService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CountryService extends CachedBaseService<Country, BCountry, String, CountryRepository> {

    @Inject
    public CountryService(CountryRepository repository) {
        super(repository, new CountryConversion());
    }
}
