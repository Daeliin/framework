package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.resource.service.PagingService;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.resource.Persistable;
import com.daeliin.components.webservices.exception.PageRequestException;
import com.daeliin.components.webservices.exception.ResourceNotFoundException;
import com.daeliin.components.webservices.exception.ResourceUpdateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/***
 * Exposes CRUD and pagination for a resource.
 * @param <T> resource type
 * @param <S> resource service
 */
public abstract class ResourceController<T extends Persistable<ID>, ID, S extends PagingService<T, ID>> implements PagingController<T, ID> {
    
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "20";
    public static final String DEFAULT_DIRECTION = "ASC";
    public static final String DEFAULT_PROPERTIES = "id";
    
    @Inject
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
    public T create(@RequestBody T resource) {
        return service.create(resource);
    }
    
    /**
     * Exposes a search by id entry point, returns the resource and a 200 if a resource exist for this id
     * and a 404 otherwise.
     * @param resourceId resource id
     * @return resource
     */
    @RequestMapping(value="{resourceId}", method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public T getOne(@PathVariable ID resourceId) {
        try {
            return service.findOne(resourceId);
        } catch (PersistentResourceNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Exposes a pagination entry point, returns the resource page and a 200, or a 400 if one of the parameters is not valid.
     * @param page 0-based page index
     * @param size page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws PageRequestException if pageNumber &lt; 0, pageSize &lt; 0, direction doesnt equal "asc" or "desc  
     */
    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public Page<T> getAll(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) String page,
        @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) String size,
        @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION) String direction,
        @RequestParam(value = "properties", defaultValue = DEFAULT_PROPERTIES) String... properties) {

        PageRequestValidation pageRequestValidation = new PageRequestValidation(page, size, direction, properties);
        PageRequest pageRequest = new PageRequest(pageRequestValidation.index, pageRequestValidation.size, pageRequestValidation.sorts);

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
    @RequestMapping(value="{resourceId}", method = PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public T update(@PathVariable ID resourceId, @RequestBody T resource) {
        if (!service.exists(resourceId)) {
            throw new ResourceNotFoundException();
        }

        if (!resourceId.equals(resource.id())) {
            throw new ResourceUpdateRequestException("The update resource doesn't match the id");
        }

        return service.update(resource);
    }

    /**
     * Exposes a delete by id entry point, returns a 410 if the resource is found, a 404 otherwise.
     * @param resourceId resource id to delete
     */
    @RequestMapping(value="{resourceId}", method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@PathVariable ID resourceId) {
        boolean deleted = service.delete(resourceId);

        if (!deleted) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Exposes a delete entry point for a list of ids, returns a 410,
     * it's exposed as a POST and not a DELETE because if the list of ids is passed as a request parameters,
     * which is the only way of passing data for a DELETE request since it has no body,
     * the URL maximum size limits the number of ids we can pass.
     * @param resourceIds resource ids to delete
     */
    @RequestMapping(value="deleteSeveral", method = POST)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@RequestBody Collection<ID> resourceIds) {
        service.delete(resourceIds);
    }
}
