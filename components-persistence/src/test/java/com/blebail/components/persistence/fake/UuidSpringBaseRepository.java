package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.repository.SpringBaseRepository;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import com.blebail.querydsl.crud.commons.resource.QDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class UuidSpringBaseRepository extends SpringBaseRepository<QUuidResource, BUuidResource> {

    @Inject
    public UuidSpringBaseRepository(SQLQueryFactory queryFactory) {
        super(new QDSLResource<>(QUuidResource.uuidResource), queryFactory);
    }
}