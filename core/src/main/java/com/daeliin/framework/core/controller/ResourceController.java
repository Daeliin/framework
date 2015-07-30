package com.daeliin.framework.core.controller;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.core.exception.ResourceNotFoundException;
import com.daeliin.framework.core.service.FullCrudService;
import java.io.Serializable;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/***
 * Exposes CRUD and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 * @param <S> resource service
 */
@RestController
public abstract class ResourceController<E extends PersistentResource, ID extends Serializable, S extends FullCrudService<E, ID>> implements FullCrudController<E, ID> {
    
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "20";
    private static final String DEFAULT_PAGE_DIRECTION = "ASC";
    private static final String DEFAULT_PAGE_PROPERTIES = "id";
    
    @Autowired
    protected S service;

    /**
     * Exposes a create entry point, returns the created resource and a 201 if the resource is valid and a 400 otherwise.
     * @param resource resource to create
     * @return created resource
     */
    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Override
    public E create(@RequestBody @Valid E resource) {
        return service.save(resource);
    }
    
    /**
     * Exposes a search by id entry point, returns the resource and a 200 if a resource exist for this id
     * and a 404 otherwise.
     * @param id resource id
     * @return resource
     */
    @RequestMapping(value="{id}", method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public E getOne(@PathVariable ID id) throws ResourceNotFoundException {
        return service.findOne(id);
    }
    
    /**
     * Exposes a pagination entry point, returns the resource page and a 200.
     * @param pageNumber page number
     * @param pageSize page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     */
    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public Page<E> getAll(
        @RequestParam(value = "pageNumber", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber, 
        @RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize, 
        @RequestParam(value = "direction", required = false, defaultValue = DEFAULT_PAGE_DIRECTION) Sort.Direction direction, 
        @RequestParam(value = "properties", required = false, defaultValue = DEFAULT_PAGE_PROPERTIES) String... properties) {
        
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize, direction, properties);
        return service.findAll(pageRequest);
    }
    
    /**
     * Exposes an update entry point, returns the updated resource and a 200 if the resource is valid, and a 400 otherwise.
     * @param resource resource to update
     * @return updated resource 
     */
    @RequestMapping(method = PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public E update(@RequestBody @Valid E resource) {
        return service.save(resource);
    }

    /**
     * Exposes a delete by id entry point, returns a 410.
     * @param id resource id to delete
     */
    @RequestMapping(value="{id}", method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }
    
    /**
     * Exposes a delete entry point, returns a 410.
     */
    @RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void deleteAll() {
        service.deleteAll();
    }
}
