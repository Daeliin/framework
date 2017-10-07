package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.service.ResourceService;
import com.daeliin.components.persistence.sql.BUuidPersistentResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidPersistentResourceService extends ResourceService<UuidPersistentResource, BUuidPersistentResource, String, UuidPersistentResourceRepository> {

    @Inject
    public UuidPersistentResourceService(UuidPersistentResourceRepository repository) {
        super(repository, new UuidPersistentResourceConversion());
    }
}
