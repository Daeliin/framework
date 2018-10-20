package com.blebail.components.webservices.controller;

import com.blebail.components.persistence.resource.Persistable;
import com.blebail.components.persistence.resource.service.CrudService;
import com.blebail.components.webservices.dto.ResourceDtoConversion;
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
import java.util.NoSuchElementException;

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
public abstract class BaseController<V, T extends Persistable<ID>, ID, S extends CrudService<T, ID>> implements CrudController<V, ID> {

    protected final S service;
    protected final ResourceDtoConversion<V, T, ID> conversion;

    @Inject
    public BaseController(S service, ResourceDtoConversion<V, T, ID> conversion) {
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
        return conversion.from(service.create(conversion.to(resource, null, Instant.now())));
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
        return conversion.from(service.findOne(resourceId));
    }

    /**
     * Returns all resources.
     * @return all resources
     */
    public Collection<V> getAll() {
        return conversion.from(service.findAll());
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
            throw new NoSuchElementException();
        }

        T existingResource = service.findOne(resourceId);

        return conversion.from(service.update(conversion.to(resource, existingResource.id(), existingResource.creationDate())));
    }

    /**
     * Exposes a delete by id entry point, returns a 204 if the resource is found, a 404 otherwise.
     * @param resourceId resource id to delete
     */
    @RequestMapping(value="{resourceId}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable ID resourceId) {
        if (!service.exists(resourceId)) {
            throw new NoSuchElementException();
        }

        service.delete(resourceId);
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
