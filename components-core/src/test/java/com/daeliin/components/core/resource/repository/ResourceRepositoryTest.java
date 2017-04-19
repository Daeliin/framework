package com.daeliin.components.core.resource.repository;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UuidPersistentResourceRepository;
import com.daeliin.components.core.fixtures.UuidPersistentResourceFixtures;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class ResourceRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UuidPersistentResourceRepository repository;

    @Test(expected = Exception.class)
    public void shouldThrowException_whenPersistingNull() {
        BUuidPersistentResource nullUuidEntity = null;

        repository.save(nullUuidEntity);
    }

    @Test
    public void shouldPersistAResource() {
        BUuidPersistentResource newUuuidPersistentResource = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label100", UUID.randomUUID().toString());
        int uuidPersistentResourceCountBeforeCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        BUuidPersistentResource persistedUuidEntity = repository.findOne(repository.save(newUuuidPersistentResource).getUuid());

        int uuidPersistentResourceCountAfterCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldReturnThePersistedResource() {
        BUuidPersistentResource newUuuidPersistentResource = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label100", UUID.randomUUID().toString());

        BUuidPersistentResource returnedUuidEntity = repository.save(newUuuidPersistentResource);

        assertThat(returnedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldPersistResources() {
        BUuidPersistentResource newUuuidPersistentResource1 = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label101", UUID.randomUUID().toString());
        BUuidPersistentResource newUuuidPersistentResource2 = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label102", UUID.randomUUID().toString());
        List<BUuidPersistentResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        int uuidPersistentResourceCountBeforeCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.save(newUuidEntities);

        int uuidPersistentResourceCountAterCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountAterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    public void shouldReturnThePersistedResources() {
        BUuidPersistentResource newUuuidPersistentResource1 = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label101", UUID.randomUUID().toString());
        BUuidPersistentResource newUuuidPersistentResource2 = new BUuidPersistentResource(Timestamp.valueOf(LocalDateTime.now()), "label102", UUID.randomUUID().toString());
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
    public void shouldCountResources() {
        assertThat(repository.count()).isEqualTo(4);
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
        assertThat(repository.findOne(null)).isNull();
    }

    @Test
    public void shouldFindAllResources() {
        Collection<BUuidPersistentResource> uuidEntities = repository.findAll();

        assertThat(uuidEntities.size()).isEqualTo(countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName()));
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
    public void shouldFindPage1WithSize5SortedByIdDesc() {
        int uuidPersistentResourceCount = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

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
        int uuidPersistentResourceCount = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

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
        int uuidPersistentResourceCountBeforeDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete("6984684-685648");

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNullId() {
        String nullId = null;
        int uuidPersistentResourceCountBeforeDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete(nullId);

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldDeleteAResource() {
        int uuidPersistentResourceCountBeforeCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete(UuidPersistentResourceFixtures.uuidPersistentResource1().getUuid());

        int uuidPersistentResourceCountAfterCreate = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

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
        int uuidPersistentResourceCountBeforeDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

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
        int uuidPersistentResourceCountBeforeDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

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

        int uuidPersistentResourceCountBeforeDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        repository.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - 2);
    }

    @Test
    public void shouldDeleteAllResources() {
        repository.deleteAll();

        int uuidPersistentResourceCountAfterDelete = countRowsInTable(QUuidPersistentResource.uuidPersistentResource.getTableName());

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(0);
    }
}
