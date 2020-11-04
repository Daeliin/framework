package com.blebail.components.persistence.resource.repository;

import com.blebail.querydsl.crud.commons.page.Page;
import com.blebail.querydsl.crud.commons.page.PageRequest;
import com.blebail.querydsl.crud.commons.resource.QDSLResource;
import com.blebail.querydsl.crud.sync.repository.BaseRepository;
import com.blebail.querydsl.crud.sync.repository.QDSLBaseRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public class SpringBaseRepository<T extends RelationalPathBase<R>, R> implements BaseRepository<R> {

    private final BaseRepository<R> repository;

    protected final SQLQueryFactory queryFactory;

    public SpringBaseRepository(QDSLResource<T, R> qdslResource, SQLQueryFactory queryFactory) {
        repository = new QDSLBaseRepository<>(qdslResource, queryFactory);
        this.queryFactory = queryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<R> findOne(Predicate predicate) {
        return repository.findOne(predicate);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> find(Predicate predicate) {
        return repository.find(predicate);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<R> find(PageRequest pageRequest) {
        return repository.find(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<R> find(Predicate predicate, PageRequest pageRequest) {
        return repository.find(predicate, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<R> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public long count(Predicate predicate) {
        return repository.count(predicate);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }

    @Transactional
    @Override
    public boolean delete(Predicate predicate) {
        return repository.delete(predicate);
    }

    @Transactional
    @Override
    public boolean deleteAll() {
        return repository.deleteAll();
    }
}
