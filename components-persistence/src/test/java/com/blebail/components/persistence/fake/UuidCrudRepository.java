package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class UuidCrudRepository extends SpringCrudRepository<QUuidResource, BUuidResource, String> {

    @Inject
    public UuidCrudRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QUuidResource.uuidResource, QUuidResource.uuidResource.uuid, BUuidResource::getUuid), queryFactory);
    }
}