package com.blebail.components.persistence.resource.repository;


import com.blebail.components.persistence.fake.UuidSpringBaseRepository;
import com.blebail.components.persistence.fixtures.JavaFixtures;
import com.blebail.components.persistence.fixtures.UuidResourceRows;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import com.blebail.junit.SqlFixture;
import com.blebail.junit.SqlMemoryDb;
import com.blebail.querydsl.crud.commons.page.Page;
import com.blebail.querydsl.crud.commons.page.PageRequest;
import com.blebail.querydsl.crud.commons.page.Sort;
import com.blebail.querydsl.crud.commons.utils.Factories;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SpringBaseRepositoryIT {

    @Inject
    private UuidSpringBaseRepository tested;

    @RegisterExtension
    public static SqlMemoryDb sqlMemoryDb = new SqlMemoryDb();

    @RegisterExtension
    public SqlFixture dbFixture = new SqlFixture(sqlMemoryDb::dataSource, JavaFixtures.uuidResources());

    @Test
    public void shouldCountResources() {
        dbFixture.readOnly();

        assertThat(tested.count()).isEqualTo(countRows());
    }

    @Test
    public void shouldCountAllResources_whenCountingWithNullPredicate() {
        dbFixture.readOnly();

        assertThat(tested.count(null)).isEqualTo(countRows());
    }

    @Test
    public void shouldReturnZero_whenCountingWithPredicateThatDoesntMachAnyResource() {
        dbFixture.readOnly();

        Predicate noMatchPredicate = QUuidResource.uuidResource.label.eq("Foo");
        assertThat(tested.count(noMatchPredicate)).isEqualTo(0);
    }

    @Test
    public void shouldCountResourcesWithPredicate() {
        dbFixture.readOnly();

        Predicate predicate = QUuidResource.uuidResource.label.eq(UuidResourceRows.uuidResource1().getLabel())
                .or(QUuidResource.uuidResource.label.eq(UuidResourceRows.uuidResource2().getLabel()));

        assertThat(tested.count(predicate)).isEqualTo(2);
    }

    @Test
    public void shouldThrowException_whenFindingResourceWithNullPredicate() {
        dbFixture.readOnly();

        assertThrows(NullPointerException.class, () -> tested.findOne(null));
    }

    @Test
    public void shouldReturnEmpty_whenPredicateDoesntMatchAnyRow() {
        dbFixture.readOnly();

        Predicate predicate = QUuidResource.uuidResource.label.eq("nonExistingLabel");

        assertThat(tested.findOne(predicate).isPresent()).isFalse();
    }

    @Test
    public void shouldFindResource_accordingToPredicate() {
        dbFixture.readOnly();

        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceRows.uuidResource1().getUuid());

        assertThat(tested.findOne(predicate)).usingFieldByFieldValueComparator().contains(UuidResourceRows.uuidResource1());
    }

    @Test
    public void shouldFindAllResources_whenPredicateIsNull() {
        dbFixture.readOnly();

        Collection<BUuidResource> foundUuidEntities = tested.find((Predicate) null);

        assertThat(foundUuidEntities.size()).isEqualTo(countRows());
    }

    @Test
    public void shouldReturnEmptyCollection_whenPredicateDoesntMatchAnyResource() {
        dbFixture.readOnly();

        Predicate labelIsEqualToFoo = QUuidResource.uuidResource.label.eq("Foo");

        Collection<BUuidResource> foundUuidEntities = tested.find(labelIsEqualToFoo);

        assertThat(foundUuidEntities).isEmpty();
    }

    @Test
    public void shouldFindResources_accordingToPredicate() {
        dbFixture.readOnly();

        Predicate labelStartsWithLabel = QUuidResource.uuidResource.label.startsWith("label");

        Collection<BUuidResource> foundUuidEntities = tested.find(labelStartsWithLabel);

        assertThat(foundUuidEntities).usingFieldByFieldElementComparator().containsOnly(
                UuidResourceRows.uuidResource1(),
                UuidResourceRows.uuidResource2(),
                UuidResourceRows.uuidResource3(),
                UuidResourceRows.uuidResource4()
        );
    }

    @Test
    public void shouldFindAllResources() {
        dbFixture.readOnly();

        Collection<BUuidResource> uuidEntities = tested.findAll();

        assertThat(uuidEntities.size()).isEqualTo(countRows());
    }

    @Test
    public void shouldFindPage1WithSize5SortedByIdDesc() {
        dbFixture.readOnly();

        long uuidPersistentResourceCount = countRows();

        Collection<BUuidResource> uuidPersistentResourcePageContent = Arrays.asList(
                UuidResourceRows.uuidResource2(),
                UuidResourceRows.uuidResource1());

        Page<BUuidResource> page = tested.find(new PageRequest(1, 2, List.of(new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items())
                .usingFieldByFieldElementComparator()
                .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidResource[uuidPersistentResourcePageContent.size()]));
        assertThat(page.size()).isEqualTo(uuidPersistentResourcePageContent.size());
        assertThat(page.totalItems()).isEqualTo(uuidPersistentResourceCount);
        assertThat(page.totalPages()).isEqualTo(uuidPersistentResourceCount / 2);
    }

    @Test
    public void shouldApplySortsInTheSameOrderAsTheyWereRequested() {
        dbFixture.readOnly();

        long uuidPersistentResourceCount = countRows();

        Collection<BUuidResource> uuidPersistentResourcePageContent = Arrays.asList(
                UuidResourceRows.uuidResource3(),
                UuidResourceRows.uuidResource2(),
                UuidResourceRows.uuidResource1(),
                UuidResourceRows.uuidResource4());

        Page<BUuidResource> page = tested.find(new PageRequest(0, (int) uuidPersistentResourceCount,
                List.of(new Sort("creationDate", Sort.Direction.ASC), new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items())
                .usingFieldByFieldElementComparator()
                .containsExactly(uuidPersistentResourcePageContent.toArray(new BUuidResource[uuidPersistentResourcePageContent.size()]));
    }

    @Test
    public void shouldFindPageWithPredicate() {
        dbFixture.readOnly();

        Predicate uuidDoesntContain511 = QUuidResource.uuidResource.uuid.contains("511").not();
        PageRequest pageRequest = new PageRequest(0, 2, List.of(new Sort("label", Sort.Direction.ASC)));

        Page<BUuidResource> page = tested.find(uuidDoesntContain511, pageRequest);

        assertThat(page.items()).usingFieldByFieldElementComparator().containsOnly(
                UuidResourceRows.uuidResource2(),
                UuidResourceRows.uuidResource3());

        assertThat(page.totalItems()).isEqualTo(3);
        assertThat(page.totalPages()).isEqualTo(2);
    }

    @Test
    public void shouldThrowExpcetion_whenDeletingWithNullPredicate() {
        dbFixture.readOnly();

        assertThrows(NullPointerException.class, () -> tested.delete(null));
    }

    @Test
    public void shouldNotDeleteAnyResource_whenDeletingWithPredicateThatDoesntMachAnyResource() {
        dbFixture.readOnly();

        long uuidPersistentResourceCount = countRows();

        Predicate noMatchPredicate = QUuidResource.uuidResource.label.eq("Foo");
        assertThat(tested.delete(noMatchPredicate)).isFalse();

        assertThat(countRows()).isEqualTo(uuidPersistentResourceCount);
    }

    @Test
    public void shouldDeleteResourcesWithPredicate() {
        long uuidPersistentResourceCount = countRows();

        Predicate predicate = QUuidResource.uuidResource.label.eq(UuidResourceRows.uuidResource1().getLabel())
                .or(QUuidResource.uuidResource.label.eq(UuidResourceRows.uuidResource2().getLabel()));

        assertThat(tested.delete(predicate)).isTrue();
        assertThat(countRows()).isEqualTo(uuidPersistentResourceCount - 2);
    }

    @Test
    public void shouldDeleteAllResources() {
        tested.deleteAll();

        long uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(0);
    }

    private long countRows() {
        return Factories.defaultQueryFactory(sqlMemoryDb.dataSource())
                .select(QUuidResource.uuidResource)
                .from(QUuidResource.uuidResource)
                .fetchCount();
    }
}
