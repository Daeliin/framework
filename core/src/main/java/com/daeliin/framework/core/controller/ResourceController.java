package com.daeliin.framework.core.controller;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.core.service.RepositoryBasedService;
import java.io.Serializable;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ResourceController<E extends PersistentResource, ID extends Serializable, S extends RepositoryBasedService<E, ID>> implements ServiceBasedController<E, ID> {
    
    @Autowired
    protected S service;

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public E create(@Valid E resource) {
        return service.save(resource);
    }
    
    @RequestMapping(value="{id}", method = GET)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public E getOne(@PathVariable ID id) {
        return service.findOne(id);
    }

    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Page<E> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }
    
    @RequestMapping(method = PUT)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public E update(@Valid E resource) {
        return service.save(resource);
    }

    @RequestMapping(value="{id}", method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }
    
    @RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void deleteAll() {
        service.deleteAll();
    }
}
