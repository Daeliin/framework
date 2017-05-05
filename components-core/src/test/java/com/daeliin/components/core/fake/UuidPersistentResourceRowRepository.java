package com.daeliin.components.core.fake;

import com.daeliin.components.core.resource.repository.RowRepository;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidPersistentResourceRowRepository extends RowRepository<BUuidPersistentResource> {

    public UuidPersistentResourceRowRepository() {
        super(QUuidPersistentResource.uuidPersistentResource);
    }
}