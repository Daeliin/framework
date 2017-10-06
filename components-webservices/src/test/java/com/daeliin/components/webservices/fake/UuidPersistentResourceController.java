package com.daeliin.components.webservices.fake;

import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.webservices.controller.ResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/uuid")
@RestController
public final class UuidPersistentResourceController extends ResourceController<UuidPersistentResourceDto, UuidPersistentResource, String, UuidPersistentResourceService> {

    @Inject
    public UuidPersistentResourceController(UuidPersistentResourceService service) {
        super(service, new UuidPersistentResourceDtoConversion());
    }

    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Page<UuidPersistentResourceDto> getAll(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) String page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) String size,
            @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION) String direction,
            @RequestParam(value = "properties", defaultValue = DEFAULT_PROPERTIES) String... properties) {

        return super.getAll(null, page, size, direction, properties);
    }
}
