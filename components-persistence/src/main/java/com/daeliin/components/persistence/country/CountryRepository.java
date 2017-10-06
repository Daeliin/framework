package com.daeliin.components.persistence.country;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BCountry;
import com.daeliin.components.core.sql.QCountry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CountryRepository extends ResourceRepository<BCountry, String> {

    public CountryRepository() {
        super(QCountry.country, QCountry.country.code, BCountry::getCode);
    }
}
