package com.blebail.components.webservices.controller;

import com.blebail.components.core.pagination.Page;
import com.blebail.components.core.pagination.PageRequest;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.components.persistence.resource.service.CrudService;
import com.blebail.components.webservices.dto.ResourceDtoConversion;
import com.blebail.components.webservices.validation.PageRequestValidation;
import com.querydsl.core.types.Predicate;

import javax.inject.Inject;

/***
 * Exposes CRUD and provides a simple pagination for a resource.
 * @param <V> resource view type
 * @param <T> resource type
 * @param <ID> resource ID type
 * @param <S> resource service
 */
public abstract class ResourceController<V, T extends Persistable<ID>, ID, S extends CrudService<T, ID>> extends BaseController<V, T, ID, S> {
    
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "20";
    public static final String DEFAULT_DIRECTION = "ASC";
    public static final String DEFAULT_PROPERTIES = "id";

    @Inject
    public ResourceController(S service, ResourceDtoConversion<V, T, ID> conversion) {
        super(service, conversion);
    }

    /**
     * Returns the resource page.
     * @param predicate the predicate to apply
     * @param page 0-based page index
     * @param size page size
     * @param direction sort direction
     * @param properties resource properties to sort on
     * @return resource page
     * @throws IllegalArgumentException if pageNumber &lt; 0, pageSize &lt; 0, direction doesnt equal "asc" or "desc
     */
    public Page<V> getAll(Predicate predicate, String page, String size, String direction, String... properties) {
        PageRequestValidation pageRequestValidation = new PageRequestValidation(page, size, direction, properties);
        PageRequest pageRequest = pageRequestValidation.validate();

        Page<T> pageResult = service.findAll(predicate, pageRequest);

        return new Page<>(conversion.from(pageResult.items), pageResult.totalItems, pageResult.totalPages);
    }
}
