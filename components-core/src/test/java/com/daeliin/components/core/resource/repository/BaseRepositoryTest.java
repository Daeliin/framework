package com.daeliin.components.core.resource.repository;


import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UuidPersistentResourceBaseRepository;
import com.daeliin.components.core.fixtures.UuidPersistentResourceFixtures;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class BaseRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UuidPersistentResourceBaseRepository repository;

    @Test
    public void shouldProvideTheRowPath() {
        assertThat(repository.rowPath()).isEqualTo(QUuidPersistentResource.uuidPersistentResource);
    }

    @Test
    public void shouldCountResources() {
        assertThat(repository.count()).isEqualTo(4);
    }

    @Test
    public void shouldReturnEmptyCollection_whenPredicateIsNull() {
        Predicate nullPredicate = null;

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(nullPredicate);

        assertThat(foundUuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollection_whenPredicateDoesntMatchAnyRow() {
        Predicate labelIsEqualToFoo = QUuidPersistentResource.uuidPersistentResource.label.eq("Foo");

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(labelIsEqualToFoo);

        assertThat(foundUuidEntities).isEmpty();
    }

    @Test
    public void shouldFindResource_accordingToPredicate() {
        Predicate labelStartsWithLabel = QUuidPersistentResource.uuidPersistentResource.label.startsWith("label");

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(labelStartsWithLabel);

        assertThat(foundUuidEntities).usingFieldByFieldElementComparator().containsOnly(
                UuidPersistentResourceFixtures.uuidPersistentResource1(),
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource3(),
                UuidPersistentResourceFixtures.uuidPersistentResource4()
        );
    }

    @Test
    public void shouldFindAllResources() {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll();

        assertThat(uuidEntities.size()).isEqualTo(countRows());
    }

    @Test
    public void shouldFindPage1WithSize5SortedByIdDesc() {
        int uuidPersistentResourceCount = countRows();

        Collection<BUuidPersistentResource> uuidPersistentResourcePageContent = Arrays.asList(
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource1());

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(1, 2, Sets.newHashSet(new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items)
                .usingFieldByFieldElementComparator()
                .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidPersistentResource[uuidPersistentResourcePageContent.size()]));
        assertThat(page.nbItems).isEqualTo(uuidPersistentResourcePageContent.size());
        assertThat(page.totalItems).isEqualTo(uuidPersistentResourceCount);
        assertThat(page.totalPages).isEqualTo(uuidPersistentResourceCount / 2);
    }

    @Test
    public void shouldApplySortsInTheSameOrderAsTheyWereRequested() {
        int uuidPersistentResourceCount = countRows();

        Collection<BUuidPersistentResource> uuidPersistentResourcePageContent = Arrays.asList(
                UuidPersistentResourceFixtures.uuidPersistentResource3(),
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource1(),
                UuidPersistentResourceFixtures.uuidPersistentResource4());

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(0, uuidPersistentResourceCount, Sets.newHashSet(
                new Sort("creationDate", Sort.Direction.ASC),
                new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items)
                .usingFieldByFieldElementComparator()
                .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidPersistentResource[uuidPersistentResourcePageContent.size()]));
    }

    @Test
    public void shouldDeleteAllResources() {
        repository.deleteAll();

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(0);
    }

    private int countRows() {
        return countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());
    }
}
