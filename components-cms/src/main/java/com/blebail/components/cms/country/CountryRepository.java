package com.blebail.components.cms.country;

import com.blebail.components.cms.sql.BCountry;
import com.blebail.components.cms.sql.QCountry;
import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class CountryRepository extends SpringCrudRepository<QCountry, BCountry, String> {

    @Inject
    public CountryRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QCountry.country, QCountry.country.code, BCountry::getCode), queryFactory);
    }
}
