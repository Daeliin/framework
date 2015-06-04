package com.daeliin.framework.core.controller;

import com.daeliin.framework.core.service.ResourceService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ResourceController<S extends ResourceService> implements PagingAndSortingController {
    
    protected final S service;
    
    protected ResourceController(final S service) {
        this.service = service;
    }
}
