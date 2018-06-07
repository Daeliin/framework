package com.daeliin.components.persistence.fake;

import com.daeliin.components.persistence.resource.service.ResourceService;
import com.daeliin.components.persistence.sql.BUuidResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidResourceService extends ResourceService<UuidResource, BUuidResource, String, UuidResourceRepository> {

    @Inject
    public UuidResourceService(UuidResourceRepository repository) {
        super(repository, new UuidResourceConversion());
    }
}
