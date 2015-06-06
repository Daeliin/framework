package com.daeliin.framework.core.controller;

import com.daeliin.framework.commons.model.PersistentResource;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FullCrudController<E extends PersistentResource, ID extends Serializable> {
    
    E create(E resource);
    
    E getOne(ID id);
    
    Page<E> getAll(Pageable pageable);
    
    E update(E resource);
    
    void delete(ID id);
    
    void deleteAll();
}
