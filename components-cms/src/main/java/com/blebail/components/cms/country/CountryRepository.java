package com.blebail.components.cms.country;

import com.blebail.components.cms.sql.BCountry;
import com.blebail.components.cms.sql.QCountry;
import com.blebail.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CountryRepository extends ResourceRepository<BCountry, String> {

    public CountryRepository() {
        super(QCountry.country, QCountry.country.code, BCountry::getCode);
    }
}
