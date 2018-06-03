package com.daeliin.components.persistence.resource.repository;


import com.daeliin.components.core.pagination.Page;
import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.persistence.fake.UuidPersistentResourceBaseRepository;
import com.daeliin.components.persistence.fixtures.JavaFixtures;
import com.daeliin.components.persistence.fixtures.UuidPersistentResourceRows;
import com.daeliin.components.persistence.sql.BUuidPersistentResource;
import com.daeliin.components.persistence.sql.QUuidPersistentResource;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BaseRepositoryIT {

    @Inject
    private UuidPersistentResourceBaseRepository repository;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.uuidPersistentResources());

    @Test
    public void shouldProvideTheRowPath() {
        assertThat(repository.rowPath()).isEqualTo(QUuidPersistentResource.uuidPersistentResource);

        dbFixture.noRollback();
    }

    @Test
    public void shouldCountResources() throws Exception {
        assertThat(repository.count()).isEqualTo(countRows());

        dbFixture.noRollback();
    }

    @Test
    public void shouldCountAllResources_whenCountingWithNullPredicate() throws Exception {
        Predicate nullPredicate = null;
        assertThat(repository.count(nullPredicate)).isEqualTo(countRows());

        dbFixture.noRollback();
    }

    @Test
    public void shouldReturnZero_whenCountingWithPredicateThatDoesntMachAnyResource() {
        Predicate noMatchPredicate = QUuidPersistentResource.uuidPersistentResource.label.eq("Foo");
        assertThat(repository.count(noMatchPredicate)).isEqualTo(0);

        dbFixture.noRollback();
    }

    @Test
    public void shouldCountResourcesWithPredicate() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceRows.uuidPersistentResource1().getLabel())
            .or(QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceRows.uuidPersistentResource2().getLabel()));

        assertThat(repository.count(predicate)).isEqualTo(2);

        dbFixture.noRollback();
    }

    @Test
    public void shouldReturnEmpty_whenFindingResourceWithNullPredicate() {
        Predicate nullPredicate = null;

        assertThat(repository.findOne(nullPredicate).isPresent()).isFalse();

        dbFixture.noRollback();
    }

    @Test
    public void shouldReturnEmpty_whenPredicateDoesntMatchAnyRow() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.label.eq("nonExistingLabel");

        assertThat(repository.findOne(predicate).isPresent()).isFalse();

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindResource_accordingToPredicate() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.uuid.eq(UuidPersistentResourceRows.uuidPersistentResource1().getUuid());

        assertThat(repository.findOne(predicate).get()).isEqualToComparingFieldByField(UuidPersistentResourceRows.uuidPersistentResource1());

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindAllResources_whenPredicateIsNull() throws Exception {
        Predicate nullPredicate = null;

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(nullPredicate);

        assertThat(foundUuidEntities.size()).isEqualTo(countRows());

        dbFixture.noRollback();
    }

    @Test
    public void shouldReturnEmptyCollection_whenPredicateDoesntMatchAnyResource() {
        Predicate labelIsEqualToFoo = QUuidPersistentResource.uuidPersistentResource.label.eq("Foo");

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(labelIsEqualToFoo);

        assertThat(foundUuidEntities).isEmpty();

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindResources_accordingToPredicate() {
        Predicate labelStartsWithLabel = QUuidPersistentResource.uuidPersistentResource.label.startsWith("label");

        Collection<BUuidPersistentResource> foundUuidEntities = repository.findAll(labelStartsWithLabel);

        assertThat(foundUuidEntities).usingFieldByFieldElementComparator().containsOnly(
            UuidPersistentResourceRows.uuidPersistentResource1(),
            UuidPersistentResourceRows.uuidPersistentResource2(),
            UuidPersistentResourceRows.uuidPersistentResource3(),
            UuidPersistentResourceRows.uuidPersistentResource4()
        );

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindAllResources() throws Exception {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll();

        assertThat(uuidEntities.size()).isEqualTo(countRows());

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindPage1WithSize5SortedByIdDesc() throws Exception {
        int uuidPersistentResourceCount = countRows();

        Collection<BUuidPersistentResource> uuidPersistentResourcePageContent = Arrays.asList(
            UuidPersistentResourceRows.uuidPersistentResource2(),
            UuidPersistentResourceRows.uuidPersistentResource1());

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(1, 2, Sets.newLinkedHashSet(
            Arrays.asList(new Sort("uuid", Sort.Direction.DESC)))));

        assertThat(page.items)
            .usingFieldByFieldElementComparator()
            .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidPersistentResource[uuidPersistentResourcePageContent.size()]));
        assertThat(page.nbItems).isEqualTo(uuidPersistentResourcePageContent.size());
        assertThat(page.totalItems).isEqualTo(uuidPersistentResourceCount);
        assertThat(page.totalPages).isEqualTo(uuidPersistentResourceCount / 2);

        dbFixture.noRollback();
    }

    @Test
    public void shouldApplySortsInTheSameOrderAsTheyWereRequested() throws Exception {
        int uuidPersistentResourceCount = countRows();

        Collection<BUuidPersistentResource> uuidPersistentResourcePageContent = Arrays.asList(
            UuidPersistentResourceRows.uuidPersistentResource3(),
            UuidPersistentResourceRows.uuidPersistentResource2(),
            UuidPersistentResourceRows.uuidPersistentResource1(),
            UuidPersistentResourceRows.uuidPersistentResource4());

        Page<BUuidPersistentResource> page = repository.findAll(new PageRequest(0, uuidPersistentResourceCount, Sets.newLinkedHashSet(
            Arrays.asList(new Sort("creationDate", Sort.Direction.ASC), new Sort("uuid", Sort.Direction.DESC)))));

        assertThat(page.items)
            .usingFieldByFieldElementComparator()
            .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidPersistentResource[uuidPersistentResourcePageContent.size()]));

        dbFixture.noRollback();
    }

    @Test
    public void shouldFindPageWithPredicate() {
        Predicate uuidDoesntContain511 = QUuidPersistentResource.uuidPersistentResource.uuid.contains("511").not();
        PageRequest pageRequest = new PageRequest(0, 2, Sets.newLinkedHashSet(Arrays.asList(new Sort("label", Sort.Direction.ASC))));

        Page<BUuidPersistentResource> page = repository.findAll(uuidDoesntContain511, pageRequest);

        assertThat(page.items).usingFieldByFieldElementComparator().containsOnly(
            UuidPersistentResourceRows.uuidPersistentResource2(),
            UuidPersistentResourceRows.uuidPersistentResource3());

        assertThat(page.totalItems).isEqualTo(3);
        assertThat(page.totalPages).isEqualTo(2);

        dbFixture.noRollback();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExpcetion_whenDeletingWithNullPredicate() {
        Predicate nullPredicate = null;
        repository.delete(nullPredicate);

        dbFixture.noRollback();
    }

    @Test
    public void shouldNotDeleteAnyResource_whenDeletingWithPredicateThatDoesntMachAnyResource() throws Exception {
        int uuidPersistentResourceCount = countRows();

        Predicate noMatchPredicate = QUuidPersistentResource.uuidPersistentResource.label.eq("Foo");
        assertThat(repository.delete(noMatchPredicate)).isFalse();

        assertThat(countRows()).isEqualTo(uuidPersistentResourceCount);

        dbFixture.noRollback();
    }

    @Test
    public void shouldDeleteResourcesWithPredicate() throws Exception{
        int uuidPersistentResourceCount = countRows();

        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceRows.uuidPersistentResource1().getLabel())
                .or(QUuidPersistentResource.uuidPersistentResource.label.eq(UuidPersistentResourceRows.uuidPersistentResource2().getLabel()));

        assertThat(repository.delete(predicate)).isTrue();
        assertThat(countRows()).isEqualTo(uuidPersistentResourceCount - 2);
    }

    @Test
    public void shouldDeleteAllResources() throws Exception {
        repository.deleteAll();

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(0);
    }

    private int countRows() throws Exception {
        return dbMemory.countRows(QUuidPersistentResource.uuidPersistentResource.getTableName());
    }
}
