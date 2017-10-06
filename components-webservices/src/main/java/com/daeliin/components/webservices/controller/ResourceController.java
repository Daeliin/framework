package com.daeliin.components.webservices.controller;

import com.daeliin.components.persistence.exception.PersistentResourceAlreadyExistsException;
import com.daeliin.components.persistence.exception.PersistentResourceNotFoundException;
import com.daeliin.components.persistence.resource.service.PagingService;
import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.resource.Persistable;
import com.daeliin.components.webservices.dto.ResourceDtoConversion;
import com.daeliin.components.webservices.exception.PageRequestException;
import com.daeliin.components.webservices.exception.ResourceAlreadyExistsException;
import com.daeliin.components.webservices.exception.ResourceNotFoundException;
import com.daeliin.components.webservices.validation.PageRequestValidation;
import com.querydsl.core.types.Predicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/***
 * Exposes CRUD and provides a simple pagination for a resource.
 * @param <V> resource view type
 * @param <T> resource type
 * @param <ID> resource ID type
 * @param <S> resource service
 */
public abstract class ResourceController<V, T extends Persistable<ID>, ID, S extends PagingService<T, ID>> implements PagingController<V, ID> {
    
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "20";
    public static final String DEFAULT_DIRECTION = "ASC";
    public static final String DEFAULT_PROPERTIES = "id";
    
    protected final S service;
    protected final ResourceDtoConversion<V, T, ID> conversion;

    @Inject
    public ResourceController(S service, ResourceDtoConversion<V, T, ID> conversion) {
        this.service = service;
        this.conversion = conversion;
    }

    /**
     * Exposes a create entry point, returns the created resource and a 201 if the resource is valid and a 400 otherwise.
     * @param resource resource to create
     * @return created resource
     */
    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Override
    public V create(@RequestBody @Valid V resource) {
        try {
            return conversion.instantiate(service.create(conversion.map(resource, null, Instant.now())));
        } catch (PersistentResourceAlreadyExistsException e) {
            throw new ResourceAlreadyExistsException();
        }
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
    public V getOne(@PathVariable ID resourceId) {
        try {
            return conversion.instantiate(service.findOne(resourceId));
        } catch (PersistentResourceNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Returns the resource page.
     * @param predicate the predicate to apply
     * @param page 0-based page index
     * @param size page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws PageRequestException if pageNumber &lt; 0, pageSize &lt; 0, direction doesnt equal "asc" or "desc  
     */
    public Page<V> getAll(Predicate predicate, String page, String size, String direction, String... properties) {
        PageRequestValidation pageRequestValidation = new PageRequestValidation(page, size, direction, properties);
        PageRequest pageRequest = new PageRequest(pageRequestValidation.index, pageRequestValidation.size, pageRequestValidation.sorts);

        Page<T> pageResult = service.findAll(predicate, pageRequest);

        return new Page<>(conversion.instantiate(pageResult.items), pageResult.totalItems, pageResult.totalPages);
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
    public V update(@PathVariable ID resourceId, @RequestBody @Valid V resource) {
        if (!service.exists(resourceId)) {
            throw new ResourceNotFoundException();
        }

        T existingResource = service.findOne(resourceId);

        return conversion.instantiate(service.update(conversion.map(resource, existingResource.getId(), existingResource.getCreationDate())));
    }

    /**
     * Exposes a delete by id entry point, returns a 204 if the resource is found, a 404 otherwise.
     * @param resourceId resource id to delete
     */
    @RequestMapping(value="{resourceId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable ID resourceId) {
        boolean deleted = service.delete(resourceId);

        if (!deleted) {
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Exposes a delete entry point for a list of ids, returns a 204,
     * it's exposed as a POST and not a DELETE because if the list of ids is passed as a request parameters,
     * which is the only way of passing data for a DELETE request since it has no body,
     * the URL maximum size limits the number of ids we can pass.
     * @param resourceIds resource ids to delete
     */
    @RequestMapping(value="deleteSeveral", method = POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@RequestBody Collection<ID> resourceIds) {
        service.delete(resourceIds);
    }
}
