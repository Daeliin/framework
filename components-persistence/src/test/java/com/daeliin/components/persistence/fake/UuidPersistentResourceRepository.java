package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.persistence.sql.BUuidPersistentResource;
import com.daeliin.components.persistence.sql.QUuidPersistentResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidPersistentResourceRepository extends ResourceRepository<BUuidPersistentResource, String> {

    public UuidPersistentResourceRepository() {
        super(QUuidPersistentResource.uuidPersistentResource, QUuidPersistentResource.uuidPersistentResource.uuid, BUuidPersistentResource::getUuid);
    }
}