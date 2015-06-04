package com.daeliin.framework.core.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public abstract class ResourceService<R extends PagingAndSortingRepository> implements PagingAndSortingService {
    
    private final R repository;
    
    @Autowired
    protected ResourceService(final R repository) {
        this.repository = repository;
    }
}
