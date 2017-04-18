package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.resource.service.PagingService;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.daeliin.components.domain.resource.Persistable;
import com.daeliin.components.webservices.exception.PageRequestException;
import com.daeliin.components.webservices.exception.ResourceNotFoundException;
import com.google.common.collect.Sets;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/***
 * Exposes CRUD and pagination for a resource.
 * @param <E> resource type
 * @param <S> resource service
 */
@RestController
public abstract class ResourceController<E extends Persistable, S extends PagingService<E>> implements FullCrudController<E> {
    
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "20";
    public static final String DEFAULT_DIRECTION = "asc";
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
    public E getOne(@PathVariable String id) {
        return service.findOne(id);
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
    public Page<E> getAll(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) String page,
        @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) String size,
        @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION) String direction,
        @RequestParam(value = "properties", defaultValue = DEFAULT_PROPERTIES) String... properties) throws PageRequestException {

        // TODO: validation of input !

        List<String> propertyList = Arrays.asList(properties);
        Set<Sort> sorts = propertyList.stream().map(property -> new Sort(property, Sort.Direction.valueOf(direction))).collect(toSet());

        PageRequest pageRequest = new PageRequest(
                Integer.parseInt(page),
                Integer.parseInt(size),
                sorts);

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
    public E update(@PathVariable String id, @RequestBody @Valid E resource) {
        E persistedResource = service.findOne(id);

        if (persistedResource == null) {
            throw new ResourceNotFoundException();
        }

        return service.update(persistedResource);
    }

    /**
     * Exposes a delete by id entry point, returns a 410 if the resource is found, a 404 otherwise.
     * @param id resource id to delete
     */
    @RequestMapping(value="{id}", method = DELETE)
    @ResponseStatus(HttpStatus.GONE)
    @Override
    public void delete(@PathVariable String id) {
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
    public void delete(@RequestBody Collection<String> ids) {
        service.delete(ids);
    }
}
