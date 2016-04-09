package com.daeliin.framework.security.credentials;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.core.resource.controller.ResourceController;
import static com.daeliin.framework.core.resource.controller.ResourceController.DEFAULT_PAGE_DIRECTION;
import static com.daeliin.framework.core.resource.controller.ResourceController.DEFAULT_PAGE_NUMBER;
import static com.daeliin.framework.core.resource.controller.ResourceController.DEFAULT_PAGE_PROPERTIES;
import static com.daeliin.framework.core.resource.controller.ResourceController.DEFAULT_PAGE_SIZE;
import com.daeliin.framework.core.resource.exception.PageRequestException;
import com.daeliin.framework.core.resource.exception.ResourceNotFoundException;
import com.daeliin.framework.core.resource.service.FullCrudService;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AdminResourceController<E extends PersistentResource, ID extends Serializable, S extends FullCrudService<E, ID>> 
    extends ResourceController<E, ID, S> {
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public E create(@Valid E resource) {
        return super.create(resource);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public E update(@PathVariable ID id, @Valid E resource) {
        return super.update(id, resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<E> getAll(
        @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) String pageNumber, 
        @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) String pageSize, 
        @RequestParam(value = "direction", defaultValue = DEFAULT_PAGE_DIRECTION) String direction, 
        @RequestParam(value = "properties", defaultValue = DEFAULT_PAGE_PROPERTIES) String... properties) throws PageRequestException {
        
        return super.getAll(pageNumber, pageSize, direction, properties);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public E getOne(@PathVariable ID id) {
        return super.getOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(@PathVariable ID id) throws ResourceNotFoundException {
        super.delete(id);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(List<ID> ids) {
        super.delete(ids);
    }
}
