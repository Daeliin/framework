package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.service.ResourceService;
import com.blebail.components.persistence.sql.BUuidResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidResourceService extends ResourceService<UuidResource, BUuidResource, String, UuidCrudRepository> {

    @Inject
    public UuidResourceService(UuidCrudRepository repository) {
        super(repository, new UuidResourceConversion());
    }
}
