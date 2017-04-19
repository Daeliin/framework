package com.daeliin.components.core.fake;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BUuidEntity;
import com.daeliin.components.core.sql.QUuidEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UUIDEntityRepository extends ResourceRepository<UUIDEntity, BUuidEntity> {

    public UUIDEntityRepository() {
        super(new UUIDEntityConversion(), QUuidEntity.uuidEntity, QUuidEntity.uuidEntity.uuid);
    }
}