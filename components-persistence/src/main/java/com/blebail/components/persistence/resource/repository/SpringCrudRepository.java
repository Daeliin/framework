package com.blebail.components.persistence.resource.repository;

import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.blebail.querydsl.crud.sync.repository.CrudRepository;
import com.blebail.querydsl.crud.sync.repository.QDSLCrudRepository;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public class SpringCrudRepository<T extends RelationalPathBase<R>, R, ID> extends SpringBaseRepository<T, R> implements CrudRepository<R, ID> {

    private final CrudRepository<R, ID> repository;

    public SpringCrudRepository(IdentifiableQDSLResource<T, R, ID> qdslResource, SQLQueryFactory queryFactory) {
        super(qdslResource, queryFactory);
        this.repository = new QDSLCrudRepository<>(qdslResource, queryFactory);
    }

    @Transactional
    @Override
    public R save(R resource) {
        return repository.save(resource);
    }

    @Transactional
    @Override
    public Collection<R> save(Iterable<R> resources) {
        return repository.save(resources);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<R> findOne(ID resourceId) {
        return repository.findOne(resourceId);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> find(Iterable<ID> resourceIds) {
        return repository.find(resourceIds);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(ID resourceId) {
        return repository.exists(resourceId);
    }

    @Transactional
    @Override
    public boolean delete(ID resourceId) {
        return repository.delete(resourceId);
    }

    @Transactional
    @Override
    public boolean delete(Iterable<ID> resourceIds) {
        return repository.delete(resourceIds);
    }
}
