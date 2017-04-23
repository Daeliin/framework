package com.daeliin.components.webservices.fake;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidPersistentResourceRepository extends ResourceRepository<BUuidPersistentResource, String> {

    public UuidPersistentResourceRepository() {
        super(QUuidPersistentResource.uuidPersistentResource, QUuidPersistentResource.uuidPersistentResource.uuid, BUuidPersistentResource::getUuid);
    }
}