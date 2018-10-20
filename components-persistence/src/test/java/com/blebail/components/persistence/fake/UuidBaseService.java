package com.blebail.components.persistence.fake;

import com.blebail.components.persistence.resource.service.BaseService;
import com.blebail.components.persistence.sql.BUuidResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UuidBaseService extends BaseService<UuidResource, BUuidResource, String, UuidResourceRepository> {

    @Inject
    public UuidBaseService(UuidResourceRepository repository) {
        super(repository, new UuidResourceConversion());
    }
}
