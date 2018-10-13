package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.repository.ResourceRepository;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidResourceRepository extends ResourceRepository<BUuidResource, String> {

    public UuidResourceRepository() {
        super(QUuidResource.uuidResource, QUuidResource.uuidResource.uuid, BUuidResource::getUuid);
    }
}