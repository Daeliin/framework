package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.repository.BaseRepository;
import com.daeliin.components.persistence.sql.BUuidResource;
import com.daeliin.components.persistence.sql.QUuidResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UuidResourceBaseRepository extends BaseRepository<BUuidResource> {

    public UuidResourceBaseRepository() {
        super(QUuidResource.uuidResource);
    }
}