package com.daeliin.components.core.resource.repository;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UUIDEntity;
import com.daeliin.components.core.fake.UUIDEntityRepository;
import com.daeliin.components.core.fixtures.UUIDEntityFixtures;
import com.daeliin.components.core.sql.QUuidEntity;
import com.daeliin.components.domain.pagination.Page;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class ResourceRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UUIDEntityRepository repository;

    @Test(expected = Exception.class)
    public void shouldThrowException_whenPersistingNull() {
        UUIDEntity nullUuidEntity = null;

        repository.save(nullUuidEntity);
    }

    @Test
    public void shouldPersistAResource() {
        UUIDEntity newUuuidEntity = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label100");
        int uuidEntityCountBeforeCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        UUIDEntity persistedUuidEntity = repository.findOne(repository.save(newUuuidEntity).uuid());

        int uuidEntityCountAfterCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterCreate).isEqualTo(uuidEntityCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldReturnThePersistedResource() {
        UUIDEntity newUuuidEntity = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label100");

        UUIDEntity returnedUuidEntity = repository.save(newUuuidEntity);

        assertThat(returnedUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldPersistResources() {
        UUIDEntity newUuidEntity1 = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UUIDEntity newUuidEntity2 = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label102");
        List<UUIDEntity> newUuidEntities = Arrays.asList(newUuidEntity1, newUuidEntity2);

        int uuidEntityCountBeforeCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.save(newUuidEntities);

        int uuidEntityCountAterCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAterCreate).isEqualTo(uuidEntityCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    public void shouldReturnThePersistedResources() {
        UUIDEntity newUuidEntity1 = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UUIDEntity newUuidEntity2 = new UUIDEntity(UUID.randomUUID().toString(), LocalDateTime.now(), "label102");
        List<UUIDEntity> newUuidEntities = Arrays.asList(newUuidEntity1, newUuidEntity2);

        List<UUIDEntity> returnedUuidEntities = (List<UUIDEntity>)repository.save(newUuidEntities);

        assertThat(returnedUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }

    @Test
    public void shouldCheckIfResourceExists() {
        assertThat(repository.exists(UUIDEntityFixtures.uuidEntity1().uuid())).isTrue();
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
        UUIDEntity uuidEntity1 = UUIDEntityFixtures.uuidEntity1();

        UUIDEntity foundUuidEntity = repository.findOne(uuidEntity1.uuid());

        assertThat(foundUuidEntity).isEqualTo(UUIDEntityFixtures.uuidEntity1());
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
        Collection<UUIDEntity> uuidEntities = repository.findAll();

        assertThat(uuidEntities.size()).isEqualTo(countRowsInTable(QUuidEntity.uuidEntity.getTableName()));
    }

    @Test
    public void shouldFindResources() {
        List<String> uuidEntityIds = Arrays.asList(UUIDEntityFixtures.uuidEntity1().uuid(), UUIDEntityFixtures.uuidEntity2().uuid());
        Collection<UUIDEntity> uuidEntities = repository.findAll(uuidEntityIds);

        assertThat(uuidEntities).containsOnly(UUIDEntityFixtures.uuidEntity1(), UUIDEntityFixtures.uuidEntity2());
    }

    @Test
    public void shouldReturnNoResources_whenFindingNonExistingResources() {
        Collection<UUIDEntity> uuidEntities = repository.findAll(Arrays.asList("68464-684", "684684-444"));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingZeroResources() {
        Collection<UUIDEntity> uuidEntities = repository.findAll(Arrays.asList());

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingNulls() {
        Collection<UUIDEntity> uuidEntities = repository.findAll(Arrays.asList(null, null));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnOnlyExistingResources_whenFindingEexistingAndNonExistingResources() {
        List<String> uuidEntityIds = Arrays.asList(UUIDEntityFixtures.uuidEntity1().uuid(), UUIDEntityFixtures.uuidEntity2().uuid(), "646444-218");
        Collection<UUIDEntity> uuidEntities = repository.findAll(uuidEntityIds);

        assertThat(uuidEntities).containsOnly(UUIDEntityFixtures.uuidEntity1(), UUIDEntityFixtures.uuidEntity2());
    }

    @Test
    public void shouldFindPage1WithSize5SortedByIdDesc() {
        int uuidEntityCount = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        Collection<UUIDEntity> uuidEntityPageContent = Arrays.asList(
                UUIDEntityFixtures.uuidEntity2(),
                UUIDEntityFixtures.uuidEntity1());

        Page<UUIDEntity> page = repository.findAll(new PageRequest(1, 2, Sets.newHashSet(new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items).containsExactly(uuidEntityPageContent.toArray(new UUIDEntity[uuidEntityPageContent.size()]));
        assertThat(page.nbItems).isEqualTo(uuidEntityPageContent.size());
        assertThat(page.totalItems).isEqualTo(uuidEntityCount);
        assertThat(page.totalPages).isEqualTo(uuidEntityCount / 2);
    }

    @Test
    public void shouldApplySortsInTheSameOrderAsTheyWereRequested() {
        int uuidEntityCount = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        Collection<UUIDEntity> uuidEntityPageContent = Arrays.asList(
                UUIDEntityFixtures.uuidEntity3(),
                UUIDEntityFixtures.uuidEntity2(),
                UUIDEntityFixtures.uuidEntity1(),
                UUIDEntityFixtures.uuidEntity4());

        Page<UUIDEntity> page = repository.findAll(new PageRequest(0, uuidEntityCount, Sets.newHashSet(
                new Sort("creationDate", Sort.Direction.ASC),
                new Sort("uuid", Sort.Direction.DESC))));

        assertThat(page.items).containsExactly(uuidEntityPageContent.toArray(new UUIDEntity[uuidEntityPageContent.size()]));
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
        int uuidEntityCountBeforeDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete("6984684-685648");

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountBeforeDelete).isEqualTo(uuidEntityCountAfterDelete);
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNullId() {
        String nullId = null;
        int uuidEntityCountBeforeDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete(nullId);

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountBeforeDelete).isEqualTo(uuidEntityCountAfterDelete);
    }


    @Test
    public void shouldDeleteAResource() {
        int uuidEntityCountBeforeCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete(UUIDEntityFixtures.uuidEntity1().uuid());

        int uuidEntityCountAfterCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterCreate).isEqualTo(uuidEntityCountBeforeCreate - 1);
    }

    @Test
    public void shouldReturnTrue_whenDeletingResource() {
        boolean delete = repository.delete(UUIDEntityFixtures.uuidEntity1().uuid());

        assertThat(delete).isTrue();
    }

    @Test
    public void shouldDeleteMultipleResources() {
        List<String> uuidEntityIds = Arrays.asList(UUIDEntityFixtures.uuidEntity1().uuid(), UUIDEntityFixtures.uuidEntity2().uuid());
        int uuidEntityCountBeforeDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete(uuidEntityIds);

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterDelete).isEqualTo(uuidEntityCountBeforeDelete - uuidEntityIds.size());
    }

    @Test
    public void shouldReturnTrue_whenDeletingMultipleResources() {
        List<String> uuidEntityIds = Arrays.asList(UUIDEntityFixtures.uuidEntity1().uuid(), UUIDEntityFixtures.uuidEntity2().uuid());
        boolean delete = repository.delete(uuidEntityIds);

        assertThat(delete).isTrue();
    }


    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNonExistingResources() {
        List<String> uuidEntityIds = Arrays.asList("1111-222", "313213-321321");
        int uuidEntityCountBeforeDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete(uuidEntityIds);

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterDelete).isEqualTo(uuidEntityCountBeforeDelete);
    }

    @Test
    public void shouldReturnFalse_whenNotDeletingAnyResources() {
        List<String> uuidEntityIds = Arrays.asList("1111-222", "313213-321321");
        boolean delete = repository.delete(uuidEntityIds);

        assertThat(delete).isFalse();
    }

    @Test
    public void shouldDeleteOnlyExistingResources_whenDeletingExistingAndNonExistingResources() {
        List<String> uuidEntityIds = Arrays.asList(UUIDEntityFixtures.uuidEntity1().uuid(), UUIDEntityFixtures.uuidEntity2().uuid(), "665321-111");

        int uuidEntityCountBeforeDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.delete(uuidEntityIds);

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterDelete).isEqualTo(uuidEntityCountBeforeDelete - 2);
    }

    @Test
    public void shouldDeleteAllResources() {
        repository.deleteAll();

        int uuidEntityCountAfterDelete = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterDelete).isEqualTo(0);
    }
}
