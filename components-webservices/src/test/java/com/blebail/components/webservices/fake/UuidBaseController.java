package com.blebail.components.webservices.fake;

import com.blebail.components.webservices.controller.ResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/uuid/base")
@RestController
public class UuidBaseController extends ResourceController<UuidResourceDto, UuidResource, String, UuidResourceService> {

    @Inject
    public UuidBaseController(UuidResourceService service) {
        super(service, new UuidResourceDtoConversion());
    }

    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<UuidResourceDto> getAll() {
        return super.getAll();
    }
}
