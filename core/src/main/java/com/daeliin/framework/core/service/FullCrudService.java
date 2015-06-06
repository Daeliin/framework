package com.daeliin.framework.core.service;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface FullCrudService<E extends PersistentResource, ID extends Serializable> {
    
    E save(E resource);

    Iterable<E> save(Iterable<E> iterable);
    
    boolean exists(ID id);
    
    long count();
    
    E findOne(ID id);
    
    Iterable<E> findAll();
    
    Iterable<E> findAll(Sort sort);

    Page<E> findAll(Pageable pageable);
    
    Iterable<E> findAll(Iterable<ID> iterable);

    void delete(ID id);

    void delete(E resource);

    void delete(Iterable<? extends E> iterable);

    void deleteAll();
}
