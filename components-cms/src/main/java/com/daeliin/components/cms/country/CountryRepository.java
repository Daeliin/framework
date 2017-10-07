package com.daeliin.components.cms.country;

import com.daeliin.components.cms.sql.BCountry;
import com.daeliin.components.cms.sql.QCountry;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CountryRepository extends ResourceRepository<BCountry, String> {

    public CountryRepository() {
        super(QCountry.country, QCountry.country.code, BCountry::getCode);
    }
}
