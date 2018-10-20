package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.pagination.Page;
import com.blebail.components.core.pagination.PageRequest;
import com.blebail.components.core.resource.Conversion;
import com.blebail.components.persistence.resource.Persistable;
import com.blebail.components.persistence.resource.repository.CrudRepository;
import com.querydsl.core.types.Predicate;

import java.util.Collection;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
public abstract class ResourceService<T extends Persistable<ID>, R, ID, P extends CrudRepository<R, ID>>
        extends BaseService<T, R, ID, P> implements PagingService<T, ID> {

    public ResourceService(P repository, Conversion<T, R> conversion) {
        super(repository, conversion);
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
    public Collection<T> findAll(Predicate predicate) {
        return conversion.from(repository.findAll(predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> findAll(PageRequest pageRequest) {
        Page<R> rowPage = repository.findAll(pageRequest);

        return new Page<>(conversion.from(rowPage.items), rowPage.totalItems, rowPage.totalPages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<T> findAll(Predicate predicate, PageRequest pageRequest) {
        Page<R> rowPage = repository.findAll(predicate, pageRequest);

        return new Page<>(conversion.from(rowPage.items), rowPage.totalItems, rowPage.totalPages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Predicate predicate) {
        return repository.delete(predicate);
    }
}
