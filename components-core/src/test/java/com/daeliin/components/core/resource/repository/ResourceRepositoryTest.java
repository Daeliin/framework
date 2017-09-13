package com.daeliin.components.core.resource.repository;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UuidPersistentResourceRepository;
import com.daeliin.components.core.fixtures.UuidPersistentResourceFixtures;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class ResourceRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UuidPersistentResourceRepository repository;

    @Test
    public void shouldProvideTheIdPath() {
        assertThat(repository.idPath()).isEqualTo(QUuidPersistentResource.uuidPersistentResource.uuid);
    }

    @Test
    public void shouldProvideTheIdMapping() {
        assertThat(repository.idMapping()).isNotNull();
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenPersistingNull() {
        BUuidPersistentResource nullUuidEntity = null;

        repository.save(nullUuidEntity);
    }

    @Test
    public void shouldPersistAResource() {
        BUuidPersistentResource newUuuidPersistentResource = new BUuidPersistentResource(Instant.now(), "label100", UUID.randomUUID().toString());
        int uuidPersistentResourceCountBeforeCreate = countRows();

        BUuidPersistentResource persistedUuidEntity = repository.findOne(repository.save(newUuuidPersistentResource).getUuid());

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldReturnThePersistedResource() {
        BUuidPersistentResource newUuuidPersistentResource = new BUuidPersistentResource(Instant.now(), "label100", UUID.randomUUID().toString());

        BUuidPersistentResource returnedUuidEntity = repository.save(newUuuidPersistentResource);

        assertThat(returnedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldPersistResources() {
        BUuidPersistentResource newUuuidPersistentResource1 = new BUuidPersistentResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidPersistentResource newUuuidPersistentResource2 = new BUuidPersistentResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidPersistentResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        int uuidPersistentResourceCountBeforeCreate = countRows();

        repository.save(newUuidEntities);

        int uuidPersistentResourceCountAterCreate = countRows();

        assertThat(uuidPersistentResourceCountAterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    public void shouldReturnThePersistedResources() {
        BUuidPersistentResource newUuuidPersistentResource1 = new BUuidPersistentResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidPersistentResource newUuuidPersistentResource2 = new BUuidPersistentResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidPersistentResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        Collection<BUuidPersistentResource> returnedUuidEntities = repository.save(newUuidEntities);

        assertThat(returnedUuidEntities).containsOnly(newUuuidPersistentResource1, newUuuidPersistentResource2);
    }

    @Test
    public void shouldCheckIfResourceExists() {
        assertThat(repository.exists(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid())).isTrue();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdDoesntExist() {
        assertThat(repository.exists("894984-984")).isFalse();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdIsNull() {
        assertThat(repository.exists(null)).isFalse();
    }

    @Test
    public void shouldFindResource() {
        BUuidPersistentResource uuidPersistentResource1 = UuidPersistentResourceFixtures.uuidPersistentResource1();

        BUuidPersistentResource foundUuidEntity = repository.findOne(uuidPersistentResource1.getUuid());

        assertThat(foundUuidEntity).isEqualToComparingFieldByField(UuidPersistentResourceFixtures.uuidPersistentResource1());
    }

    @Test
    public void shouldReturnNull_whenFindingNonExistingResource() {
        assertThat(repository.findOne("6846984-864684")).isNull();
    }

    @Test
    public void shouldReturnNull_whenFindingNull() {
        String nullId = null;

        assertThat(repository.findOne(nullId)).isNull();
    }

    @Test
    public void shouldReturnNull_whenFindingResourceWithPredicate() {
        Predicate nullPredicate = null;

        assertThat(repository.findOne(nullPredicate)).isNull();
    }

    @Test
    public void shouldReturnNull_whenPredicateDoesntMatchAnyRow() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.label.eq("nonExistingLabel");

        assertThat(repository.findOne(predicate)).isNull();
    }

    @Test
    public void shouldFindResource_accordingToPredicate() {
        Predicate predicate = QUuidPersistentResource.uuidPersistentResource.uuid.eq(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid());

        assertThat(repository.findOne(predicate)).isEqualToComparingFieldByField(UuidPersistentResourceFixtures.uuidPersistentResource1());
    }

    @Test
    public void shouldFindResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid(), UuidPersistentResourceFixtures.uuidPersistentResource2().getUuid());
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidPersistentResourceFixtures.uuidPersistentResource1(), UuidPersistentResourceFixtures.uuidPersistentResource2());
    }

    @Test
    public void shouldReturnNoResources_whenFindingNonExistingResources() {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll(Arrays.asList("68464-684", "684684-444"));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingZeroResources() {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll(Arrays.asList());

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingNulls() {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll(Arrays.asList(null, null));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnOnlyExistingResources_whenFindingEexistingAndNonExistingResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid(), UuidPersistentResourceFixtures.uuidPersistentResource2().getUuid(), "646444-218");
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidPersistentResourceFixtures.uuidPersistentResource1(), UuidPersistentResourceFixtures.uuidPersistentResource2());
    }

    @Test
    public void shouldReturnFalse_whenDeletingNonExistingResource() {
        assertThat(repository.delete("96846846-4465")).isFalse();
    }

    @Test
    public void shouldReturnFalse_whenDeletingNullId() {
        String nullId = null;

        assertThat(repository.delete(nullId)).isFalse();
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNonExistingResource() {
        int uuidPersistentResourceCountBeforeDelete = countRows();

        repository.delete("6984684-685648");

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNullId() {
        String nullId = null;
        int uuidPersistentResourceCountBeforeDelete = countRows();

        repository.delete(nullId);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldDeleteAResource() {
        int uuidPersistentResourceCountBeforeCreate = countRows();

        repository.delete(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid());

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate - 1);
    }

    @Test
    public void shouldReturnTrue_whenDeletingResource() {
        boolean delete = repository.delete(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid());

        assertThat(delete).isTrue();
    }

    @Test
    public void shouldDeleteMultipleResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid(), UuidPersistentResourceFixtures.uuidPersistentResource2().getUuid());
        int uuidPersistentResourceCountBeforeDelete = countRows();

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - uuidPersistentResourceIds.size());
    }

    @Test
    public void shouldReturnTrue_whenDeletingMultipleResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid(), UuidPersistentResourceFixtures.uuidPersistentResource2().getUuid());
        boolean delete = repository.delete(uuidPersistentResourceIds);

        assertThat(delete).isTrue();
    }


    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNonExistingResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        int uuidPersistentResourceCountBeforeDelete = countRows();

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete);
    }

    @Test
    public void shouldReturnFalse_whenNotDeletingAnyResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        boolean delete = repository.delete(uuidPersistentResourceIds);

        assertThat(delete).isFalse();
    }

    @Test
    public void shouldDeleteOnlyExistingResources_whenDeletingExistingAndNonExistingResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid(), UuidPersistentResourceFixtures.uuidPersistentResource2().getUuid(), "665321-111");

        int uuidPersistentResourceCountBeforeDelete = countRows();

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - 2);
    }

    private int countRows() {
        return countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());
    }
}
