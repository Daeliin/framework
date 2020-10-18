package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.resource.Conversion;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.querydsl.crud.commons.page.Page;
import com.blebail.querydsl.crud.commons.page.PageRequest;
import com.blebail.querydsl.crud.sync.repository.CrudRepository;
import com.querydsl.core.types.Predicate;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
public abstract class BaseService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>> implements PagingService<T, ID> {

    protected final P repository;

    protected final Conversion<T, R> conversion;

    public BaseService(P repository, Conversion<T, R> conversion) {
        this.repository = Objects.requireNonNull(repository);
        this.conversion = Objects.requireNonNull(conversion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(Predicate predicate) {
        return repository.count(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> findOne(Predicate predicate) {
        Optional<R> resource = repository.findOne(predicate);

        return resource.map(conversion::from);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> find(Predicate predicate) {
        return conversion.from(repository.find(predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> find(PageRequest pageRequest) {
        Page<R> rowPage = repository.find(pageRequest);

        return new Page<>(conversion.from(rowPage.items()), rowPage.totalItems(), rowPage.totalPages());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> find(Predicate predicate, PageRequest pageRequest) {
        Page<R> rowPage = repository.find(predicate, pageRequest);

        return new Page<>(conversion.from(rowPage.items()), rowPage.totalItems(), rowPage.totalPages());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Predicate predicate) {
        return repository.delete(predicate);
    }
}
