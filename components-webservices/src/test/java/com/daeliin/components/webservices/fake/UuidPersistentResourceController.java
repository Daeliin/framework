package com.daeliin.components.webservices.fake;

import com.daeliin.components.webservices.rest.controller.ResourceController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/uuid")
@RestController
public final class UuidPersistentResourceController extends ResourceController<UuidPersistentResource, String, UuidPersistentResourceService> {
}
