package com.daeliin.components.core.fake;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BUuidEntity;
import com.daeliin.components.core.sql.QUuidEntity;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;

@Component
public final class UUIDEntityRepository extends ResourceRepository<UUIDEntity, BUuidEntity, UUIDEntityConversion> {

    @Inject
    public UUIDEntityRepository(DataSource dataSource) {
        super(dataSource, new UUIDEntityConversion(), QUuidEntity.uuidEntity, QUuidEntity.uuidEntity.id);
    }
}