package com.daeliin.components.core.fake;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidPersistentResourceService extends ResourceService<UuidPersistentResource, BUuidPersistentResource, String> {

    @Inject
    public UuidPersistentResourceService(UuidPersistentResourceRepository repository) {
        super(repository, new UuidPersistentResourceConversion());
    }
}
