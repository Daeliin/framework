package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.repository.BaseRepository;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidResourceBaseRepository extends BaseRepository<BUuidResource> {

    public UuidResourceBaseRepository() {
        super(QUuidResource.uuidResource);
    }
}