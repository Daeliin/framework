package com.daeliin.components.core.fake;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

@Component
public final class UUIDEntityRepository extends ResourceRepository<UUIDEntity, BUUIDEntity, UUIDEntityConversion> {

    @Inject
    public UUIDEntityRepository(DataSource dataSource) {
        super(dataSource, new UUIDEntityConversion(), QUUIDEntity.UUIDEntity, QUUIDEntity.UUIDEntity.id);
    }
}