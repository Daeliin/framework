package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.service.BaseService;
import com.blebail.components.persistence.sql.BUuidResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidBaseService extends BaseService<UuidResource, BUuidResource, String, UuidCrudRepository> {

    @Inject
    public UuidBaseService(UuidCrudRepository repository) {
        super(repository, new UuidResourceConversion());
    }
}
