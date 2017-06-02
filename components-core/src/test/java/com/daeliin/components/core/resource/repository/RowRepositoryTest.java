package com.daeliin.components.core.resource.repository;


import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UuidPersistentResourceRowRepository;
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
public class RowRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UuidPersistentResourceRowRepository repository;

    @Test
    public void shouldCountResources() {
        assertThat(repository.count()).isEqualTo(countRows());
    }

    @Test
    public void shouldCountAllResources_whenCountingWithNullPredicate() {
        Predicate nullPredicate = null;
        assertThat(repository.count(nullPredicate)).isEqualTo(countRows());
    }

    @Test
    public void shouldReturnZero_whenCountingWithPredicateThatDoesntMachAnyResource() {
        Predicate nullPredicate = QUuidPersistentResource.uuidPersistentResource.label.eq("Foo");
        assertThat(repository.count(nullPredicate)).isEqualTo(0);
    }

    @Test
    public void shouldCountResourcesWithPredicate() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceFixtures.uuidPersistentResource1().getLabel())
                .or(QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceFixtures.uuidPersistentResource2().getLabel()));

        assertThat(repository.count(predicate)).isEqualTo(2);
    }

    @Test
    public void shouldFindAllResources_whenPredicateIsNull() {
        Predicate nullPredicate = null;

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(nullPredicate);

        assertThat(foundUuidEntities.size()).isEqualTo(countRows());
    }

    @Test
    public void shouldReturnEmptyCollection_whenPredicateDoesntMatchAnyResource() {
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

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(1, 2, Sets.newLinkedHashSet(
                Arrays.asList(new Sort("uuid", Sort.Direction.DESC)))));

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

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(0, uuidPersistentResourceCount, Sets.newLinkedHashSet(
                Arrays.asList(new Sort("creationDate", Sort.Direction.ASC), new Sort("uuid", Sort.Direction.DESC)))));

        assertThat(page.items)
                .usingFieldByFieldElementComparator()
                .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidPersistentResource[uuidPersistentResourcePageContent.size()]));
    }

    @Test
    public void shouldFindPageWithPredicate() {
        Predicate uuidDoesntContain511 = QUuidPersistentResource.uuidPersistentResource.uuid.contains("511").not();
        PageRequest pageRequest = new PageRequest(0, 2, Sets.newLinkedHashSet(Arrays.asList(new Sort("label", Sort.Direction.ASC))));

        Page<BUuidPersistentResource> page = repository.findAll(uuidDoesntContain511, pageRequest);

        assertThat(page.items).usingFieldByFieldElementComparator().containsOnly(
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource3());

        assertThat(page.totalItems).isEqualTo(3);
        assertThat(page.totalPages).isEqualTo(2);
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
