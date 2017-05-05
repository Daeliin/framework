package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/uuid")
@RestController
public final class UuidPersistentResourceController extends ResourceController<UuidPersistentResourceDto, UuidPersistentResource, String, UuidPersistentResourceService> {

    @Inject
    public UuidPersistentResourceController(UuidPersistentResourceService service) {
        super(service, new UuidPersistentResourceDtoConversion());
    }
}
