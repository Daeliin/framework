package com.daeliin.components.core.resource.repository;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UUIDEntity;
import com.daeliin.components.core.fake.UUIDEntityRepository;
import com.daeliin.components.core.fixtures.UUIDEntityFixtures;
import com.daeliin.components.core.sql.QUuidEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class ResourceRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UUIDEntityRepository repository;

    @Test
    public void shouldPersistsAResource() {
        UUIDEntity newUuuidEntity = new UUIDEntity(100L, UUID.randomUUID().toString(), LocalDateTime.now(), "label100");
        long uuidEntityCountBeforeCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        UUIDEntity persistedUuidEntity = repository.findOne(repository.save(newUuuidEntity).id());

        long uuidEntityCountAfterCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAfterCreate).isEqualTo(uuidEntityCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldReturnThePersistedResource() {
        UUIDEntity newUuuidEntity = new UUIDEntity(100L, UUID.randomUUID().toString(), LocalDateTime.now(), "label100");

        UUIDEntity returnedUuidEntity = repository.save(newUuuidEntity);

        assertThat(returnedUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldPersistResources() {
        UUIDEntity newUuidEntity1 = new UUIDEntity(101L, UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UUIDEntity newUuidEntity2 = new UUIDEntity(102L, UUID.randomUUID().toString(), LocalDateTime.now(), "label102");
        List<UUIDEntity> newUuidEntities = Arrays.asList(newUuidEntity1, newUuidEntity2);

        long uuidEntityCountBeforeCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        repository.save(newUuidEntities);

        long uuidEntityCountAterCreate = countRowsInTable(QUuidEntity.uuidEntity.getTableName());

        assertThat(uuidEntityCountAterCreate).isEqualTo(uuidEntityCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    public void shouldReturnThePersistedResources() {
        UUIDEntity newUuidEntity1 = new UUIDEntity(101L, UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UUIDEntity newUuidEntity2 = new UUIDEntity(102L, UUID.randomUUID().toString(), LocalDateTime.now(), "label102");
        List<UUIDEntity> newUuidEntities = Arrays.asList(newUuidEntity1, newUuidEntity2);

        List<UUIDEntity> returnedUuidEntities = (List<UUIDEntity>)repository.save(newUuidEntities);

        assertThat(returnedUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }

    @Test
    public void shouldCheckIfResourceExists() {
        assertThat(repository.exists(UUIDEntityFixtures.uuidEntity1().id())).isTrue();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdDoesntExist() {
        assertThat(repository.exists(-1L)).isFalse();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdIsNull() {
        assertThat(repository.exists(null)).isFalse();
    }

    @Test
    public void shouldCountResources() {
        assertThat(repository.count()).isEqualTo(4);
    }
//
//    @Test
//    public void findOne_existingResourceId_returnsResource() {
//        UUIDEntity UUIDEntity = repository.findOne(1L);
//
//        assertEquals(UUIDEntity.getName(), "Tom");
//    }
//
//    @Test
//    public void findOne_nonExistingResourceId_returnsNull() {
//        assertNull(repository.findOne(-1L));
//    }
//
//    @Test
//    public void findAll_returnsAllResources() {
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll();
//
//        assertEquals(UUIDEntitys.size(), repository.count());
//    }
//
//    @Test
//    public void findAll_existingResourcesIds_returnsResources() {
//        List<Long> UUIDEntitysIds = Arrays.asList(1L, 2L);
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(UUIDEntitysIds);
//
//        assertEquals(UUIDEntitys.size(), UUIDEntitysIds.size());
//        UUIDEntitys.forEach((UUIDEntity UUIDEntity) -> UUIDEntitysIds.contains(UUIDEntity.getId()));
//    }
//
//    @Test
//    public void findAll_nonExistingResourcesIds_returnsNoResources() {
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(Arrays.asList(-1L, -2L));
//
//        assertEquals(UUIDEntitys.size(), 0);
//    }
//
//    @Test
//    public void findAll_noUUIDEntitysIds_returnsNoResources() {
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(new ArrayList<>());
//
//        assertEquals(UUIDEntitys.size(), 0);
//    }
//
//    @Test
//    public void findAll_existingAndNonExistingResourcesIds_returnsOnlyExistingResources() {
//        List<Long> UUIDEntitysIds = Arrays.asList(1L, 2L, -1L);
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(UUIDEntitysIds);
//
//        assertEquals(UUIDEntitys.size(), 2);
//        UUIDEntitys.forEach((UUIDEntity UUIDEntity) -> assertNotEquals(UUIDEntity.getId().longValue(), -1L));
//    }
//
//    @Test
//    public void findAll_sortByNameAsc_returnsResourcesSortedByNameAsc() {
//        List<UUIDEntity> UUIDEntitysSortedByNameAsc = (List<UUIDEntity>)repository.findAll();
//        Collections.sort(UUIDEntitysSortedByNameAsc);
//
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(new Sort(Sort.Direction.ASC, "name"));
//
//        assertEquals(UUIDEntitys, UUIDEntitysSortedByNameAsc);
//    }
//
//    @Test
//    public void findAll_sortByNameDesc_returnsResourcesSortedByNameDesc() {
//        List<UUIDEntity> UUIDEntitysSortedByNameDesc = (List<UUIDEntity>)repository.findAll();
//        Collections.sort(UUIDEntitysSortedByNameDesc);
//        Collections.reverse(UUIDEntitysSortedByNameDesc);
//
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(new Sort(Sort.Direction.DESC, "name"));
//
//        assertEquals(UUIDEntitys, UUIDEntitysSortedByNameDesc);
//    }
//
//    @Test(expected = PropertyReferenceException.class)
//    public void findAll_sortByInvalidProperty_throwsPropertyReferenceException() {
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(new Sort(Sort.Direction.DESC, "invalidProperty"));
//    }
//
//    @Test
//    public void findAll_page1WithSize5SortedByNameDesc_returnsPage1WithSize5SortedByNameDesc() {
//        List<UUIDEntity> UUIDEntitysPageContent = (List<UUIDEntity>)repository.findAll(Arrays.asList(21L, 6L, 12L, 19L, 17L));
//        Collections.sort(UUIDEntitysPageContent);
//        Collections.reverse(UUIDEntitysPageContent);
//        Page<UUIDEntity> UUIDEntitysPage = repository.findAll(new PageRequest(1, 5, Sort.Direction.DESC, "name"));
//
//        assertEquals(UUIDEntitysPage.getNumber(), 1);
//        assertEquals(UUIDEntitysPage.getSize(), 5);
//        assertEquals(UUIDEntitysPage.getNumberOfElements(), 5);
//        assertEquals(UUIDEntitysPage.getTotalElements(), 22);
//        assertEquals(UUIDEntitysPage.getTotalPages(), 5);
//        assertEquals(UUIDEntitysPage.getSort(), new Sort(Sort.Direction.DESC, "name"));
//        assertEquals(UUIDEntitysPage.getContent().size(), 5);
//
//        assertEquals(UUIDEntitysPage.getContent(), UUIDEntitysPageContent);
//    }
//
//    @Test
//    public void delete_existingResourceId_deletesResource() {
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(1L);
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test(expected = EmptyResultDataAccessException.class)
//    public void delete_nonExistingResourceId_throwsEmptyResultDataAccessException() {
//        repository.delete(-1L);
//    }
//
//    @Test
//    public void delete_nonExistingResourceId_doesntDeleteAnyResource() {
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        try {
//            repository.delete(-1L);
//        } catch (EmptyResultDataAccessException e) {
//            long UUIDEntityCountAfterDelete = repository.count();
//
//            assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete);
//            return;
//        }
//
//        fail();
//    }
//
//    @Test
//    public void delete_existingResource_deletesResource() {
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(repository.findOne(1L));
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test
//    public void delete_nonExistingResource_doesntDeleteAnyResource() {
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(new UUIDEntity().withId(-1L).withName("nonExistingUUIDEntity"));
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete);
//    }
//
//    @Test
//    public void delete_multipleExistingResources_deletesResources() {
//        List<UUIDEntity> UUIDEntitys = (List<UUIDEntity>)repository.findAll(Arrays.asList(1L, 2L));
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(UUIDEntitys);
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete - UUIDEntitys.size());
//        UUIDEntitys.forEach((UUIDEntity UUIDEntity) -> assertNull(repository.findOne(UUIDEntity.getId())));
//    }
//
//    @Test
//    public void delete_multipleNonExistingResources_doesntDeleteAnyResource() {
//        List<UUIDEntity> UUIDEntitys =
//            Arrays.asList(
//                new UUIDEntity().withId(-1L).withName("nonExistingUUIDEntity1"),
//                new UUIDEntity().withId(-2L).withName("nonExistingUUIDEntity2"));
//
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(UUIDEntitys);
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete);
//    }
//
//    @Test
//    public void delete_multipleNonExistingAndExistingResources_deletesOnlyExistingResources() {
//        List<UUIDEntity> UUIDEntitys =
//            Arrays.asList(
//                new UUIDEntity().withId(-1L).withName("nonExistingUUIDEntity1"),
//                new UUIDEntity().withId(1L).withName("Tom"));
//
//        long UUIDEntityCountBeforeDelete = repository.count();
//
//        repository.delete(UUIDEntitys);
//
//        long UUIDEntityCountAfterDelete = repository.count();
//
//        assertEquals(UUIDEntityCountAfterDelete, UUIDEntityCountBeforeDelete - 1);
//        assertNull(repository.findOne(1L));
//    }
//
//    @Test
//    public void deleteAll_deletesAllResources() {
//        repository.deleteAll();
//
//        assertEquals(repository.count(), 0);
//    }
}
