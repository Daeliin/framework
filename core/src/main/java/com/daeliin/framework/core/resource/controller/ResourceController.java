package com.daeliin.framework.core.resource.controller;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.core.resource.exception.PageRequestException;
import com.daeliin.framework.core.resource.service.FullCrudService;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String DEFAULT_PAGE_DIRECTION = "asc";
    public static final String DEFAULT_PAGE_PROPERTIES = "id";
    
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
        return service.create(resource);
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
    public E getOne(@PathVariable ID id) {
        return service.findOne(id);
    }
    
    /**
     * Exposes a pagination entry point, returns the resource page and a 200, or a 400 if one of the parameters is not valid.
     * @param pageNumber page number 0-based
     * @param pageSize page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws PageRequestException if pageNumber &lt; 0, pageSize &lt; 0, direction doesnt equal "asc" or "desc  
     */
    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public Page<E> getAll(
        @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) String pageNumber, 
        @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) String pageSize, 
        @RequestParam(value = "direction", defaultValue = DEFAULT_PAGE_DIRECTION) String direction, 
        @RequestParam(value = "properties", defaultValue = DEFAULT_PAGE_PROPERTIES) String... properties) throws PageRequestException {
        
        PageRequestParameters pageRequestParameters = new PageRequestParameters(pageNumber, pageSize, direction, properties);
        
        PageRequest pageRequest =
            new PageRequest(
                pageRequestParameters.pageNumber(), 
                pageRequestParameters.pageSize(), 
                pageRequestParameters.direction(), 
                pageRequestParameters.properties());
        
        return service.findAll(pageRequest);
    }
    
    /**
     * Exposes an update by id entry point, returns :
     * - the updated resource and a 200 if the resource is valid and found
     * - a 400 if the resource is not valid
     * - a 404 if the resource is not found.
     * @param resource resource to update
     * @return updated resource 
     */
    @RequestMapping(value="{id}", method = PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public E update(@PathVariable ID id, @RequestBody @Valid E resource) {
        return service.update(id, resource);
    }

    /**
     * Exposes a delete by id entry point, returns a 410 if the resource is found, a 404 otherwise.
     * @param id resource id to delete
     */
    @RequestMapping(value="{id}", method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }
    
    /**
     * Exposes a delete entry point for a list of ids, returns a 410,
     * it's exposed as a POST and not a DELETE because if the list of ids is passed as a request parameters,
     * which is the only way of passing data for a DELETE request since it has no body,
     * the URL maximum size limits the number of ids we can pass.
     * @param ids resource ids to delete
     */
    @RequestMapping(value="deleteSeveral", method = POST)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@RequestBody List<ID> ids) {
        service.delete(ids);
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
