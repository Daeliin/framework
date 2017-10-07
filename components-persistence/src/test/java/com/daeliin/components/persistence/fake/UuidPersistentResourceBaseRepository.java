package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.repository.BaseRepository;
import com.daeliin.components.persistence.sql.BUuidPersistentResource;
import com.daeliin.components.persistence.sql.QUuidPersistentResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidPersistentResourceBaseRepository extends BaseRepository<BUuidPersistentResource> {

    public UuidPersistentResourceBaseRepository() {
        super(QUuidPersistentResource.uuidPersistentResource);
    }
}