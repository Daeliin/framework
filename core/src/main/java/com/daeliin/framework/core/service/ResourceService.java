package com.daeliin.framework.core.service;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.core.exception.ResourceNotFoundException;
import com.daeliin.framework.core.repository.ResourceRepository;
import java.io.Serializable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Provides CRUD operations and pagination for a resource.
 * @param <E> resource type
 * @param <ID> resource id type
 * @param <R> resource repository 
 */
@Service
public abstract class ResourceService<E extends PersistentResource, ID extends Serializable, R extends ResourceRepository<E, ID>> implements FullCrudService<E, ID>  {
    
    @Autowired
    protected R repository;
    
    @Override
    public E create(E resource) {
        return repository.save(resource);
    }
    
    @Override
    public E update(ID id, E resource) throws ResourceNotFoundException {
        if (id == null || !repository.exists(id) || !resource.getId().equals(id)) {
            throw new ResourceNotFoundException();
        }
        
        return repository.save(resource);
    }

    @Override
    public Iterable<E> save(Iterable<E> iterable) {
        return repository.save(iterable);
    }

    @Override
    public boolean exists(ID id) { 
        return repository.exists(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public E findOne(ID id) throws ResourceNotFoundException {
        E resource = null;
        
        if (id != null) {
            resource = repository.findOne(id);
        }
        
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        
        return resource;
    }

    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<E> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<E> findAll(Iterable<ID> iterable) {
        return repository.findAll(iterable);
    }

    @Override
    public void delete(ID id) {
        if (!repository.exists(id)) {
            throw new ResourceNotFoundException();
        }
        
        repository.delete(id);
    }

    @Override
    public void delete(E resource) {
        repository.delete(resource);
    }

    @Override
    public void delete(Iterable<? extends E> iterable) {
        repository.delete(iterable);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
