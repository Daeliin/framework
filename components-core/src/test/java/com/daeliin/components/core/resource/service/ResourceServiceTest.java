package com.daeliin.components.core.resource.service;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.fake.UuidPersistentResource;
import com.daeliin.components.core.fake.UuidPersistentResourceRepository;
import com.daeliin.components.core.fake.UuidPersistentResourceService;
import com.daeliin.components.core.fixtures.UuidPersistentResourceFixtures;
import com.daeliin.components.domain.pagination.PageRequest;
import com.daeliin.components.domain.pagination.Sort;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = Application.class)
public class ResourceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Mock
    private UuidPersistentResourceRepository repository;

    @InjectMocks
    private UuidPersistentResourceService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnResource_whenCreatingResource() {
        UuidPersistentResource newUuuidEntity = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label100");

        doReturn(newUuuidEntity).when(repository).save(newUuuidEntity);

        UuidPersistentResource createdUuidEntity = service.create(newUuuidEntity);

        verify(repository).save(newUuuidEntity);
        assertThat(createdUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnsResources_whenCreatingResources() {
        UuidPersistentResource newUuidEntity1 = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UuidPersistentResource newUuidEntity2 = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label102");

        List<UuidPersistentResource> newUuidEntites = Arrays.asList(newUuidEntity1, newUuidEntity2);

        doReturn(newUuidEntites).when(repository).save(newUuidEntites);

        Collection<UuidPersistentResource> createdUuidEntities = service.create(newUuidEntites);

        verify(repository).save(newUuidEntites);

        assertThat(createdUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }

    @Test
    public void shouldCheckThatNullDoesntExist() {
        assertThat(service.exists(null)).isFalse();
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdExists() {
        String existingUuidEntityId = UuidPersistentResourceFixtures.uuidPersistentResource1().id();

        doReturn(true).when(repository).exists(existingUuidEntityId);

        assertThat(service.exists(existingUuidEntityId)).isTrue();
        verify(repository).exists(existingUuidEntityId);
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdDoesntExist() {
        doReturn(false).when(repository).exists("654684-64684");

        assertThat(service.exists("654684-64684")).isFalse();
        verify(repository).exists("654684-64684");
    }

    @Test
    public void shouldCallRepositoryCountAndReturnTheSameResult() {
        doReturn(5L).when(repository).count();

        assertThat(service.count()).isEqualTo(5L);
        verify(repository).count();
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenIdIsNull() {
        service.findOne(null);
    }

    @Test
    public void shouldThrowPersistentResourceNotFoundException_whenResourceIdDoesntExist() {
        doThrow(new PersistentResourceNotFoundException("")).when(repository).findOne("654684-64684");

        try {
             service.findOne("654684-64684");
        } catch (PersistentResourceNotFoundException e) {
            verify(repository).findOne("654684-64684");
            return;
        }

        fail();
    }

    @Test
    public void shouldCallRepositoryFindOneAndReturnsResource_whenFindingResource() {
        UuidPersistentResource existingUuidEntity = UuidPersistentResourceFixtures.uuidPersistentResource1();

        doReturn(existingUuidEntity).when(repository).findOne(existingUuidEntity.id());

        assertThat(service.findOne(existingUuidEntity.id())).isEqualTo(existingUuidEntity);
        verify(repository).findOne(existingUuidEntity.id());
    }

    @Test
    public void shouldCallRepositoryFindAllAndReturnsAllResources_whenFindingAllResources() {
        Collection<UuidPersistentResource> allUuidEntities = Arrays.asList(
                UuidPersistentResourceFixtures.uuidPersistentResource1(),
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource3(),
                UuidPersistentResourceFixtures.uuidPersistentResource4());

        when(repository.findAll()).thenReturn(allUuidEntities);

        Collection<UuidPersistentResource> foundUuidEntities = service.findAll();

        verify(repository).findAll();
        assertThat(foundUuidEntities).containsOnly(
                UuidPersistentResourceFixtures.uuidPersistentResource1(),
                UuidPersistentResourceFixtures.uuidPersistentResource2(),
                UuidPersistentResourceFixtures.uuidPersistentResource3(),
                UuidPersistentResourceFixtures.uuidPersistentResource4());
    }

    @Test
    public void shouldCallRepositoryFindAllWithPageRequest_whenFindingAllResoucesWithPageRequest() {
        PageRequest pageRequest = new PageRequest(1, 10, Sets.newHashSet(new Sort("id", Sort.Direction.ASC)));

        service.findAll(pageRequest);
        verify(repository).findAll(pageRequest);
    }

    @Test
    public void shouldCallRepositoryFindAllWithResourceIds_whenFindingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().id(), UuidPersistentResourceFixtures.uuidPersistentResource2().id());

        service.findAll(uuidEntityIds);
        verify(repository).findAll(uuidEntityIds);
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistenceResourceNotFoundException_whenUpdatingNullResource() {
        UuidPersistentResource nullUuidEntity = null;

        service.update(nullUuidEntity);
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistenceResourceNotFoundException_whenUpdatingNonExistingResource() {
        UuidPersistentResource nonExistingUuidEntity = new UuidPersistentResource("654684-64684", LocalDateTime.now(), "label-1");

        service.update(nonExistingUuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveWithResourceAndReturnResource_whenUpdatingResource() {
        UuidPersistentResource exitingUuidEntity = UuidPersistentResourceFixtures.uuidPersistentResource1();
        doReturn(true).when(repository).exists(exitingUuidEntity.id());
        doReturn(exitingUuidEntity).when(repository).save(exitingUuidEntity);

        UuidPersistentResource updatedUuidEntity = service.update(exitingUuidEntity);

        verify(repository).save(exitingUuidEntity);
        assertThat(updatedUuidEntity).isEqualTo(exitingUuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveWithMultipleResources_whenUpdatingResources() {
        Collection<UuidPersistentResource> existingUuidEntities = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1(), UuidPersistentResourceFixtures.uuidPersistentResource2());

        when(repository.save(existingUuidEntities)).thenReturn(existingUuidEntities);

        Collection<UuidPersistentResource> updatedUuidEntities = service.update(existingUuidEntities);

        verify(repository).save(existingUuidEntities);
        assertThat(updatedUuidEntities).isEqualTo(existingUuidEntities);
    }

    @Test
    public void shouldCallRepositoryDelete_whenDeletingResource() {
        service.delete(UuidPersistentResourceFixtures.uuidPersistentResource1().id());
        verify(repository).delete(UuidPersistentResourceFixtures.uuidPersistentResource1().id());
    }

    @Test
    public void shouldReturnFalse_whenDeletingNoResources() {
        assertThat(service.delete(Arrays.asList())).isFalse();

    }

    @Test
    public void shouldCallRepositoryDeleteWithResourceIds_whenDeletingResources() {
        UuidPersistentResource uuidEntity1 = UuidPersistentResourceFixtures.uuidPersistentResource1();
        UuidPersistentResource uuidEntity2 = UuidPersistentResourceFixtures.uuidPersistentResource2();

        service.delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));

        verify(repository).delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));
    }

    @Test
    public void shouldCallRepositoryDeleteWithResource_whenDeletingResource() {
        UuidPersistentResource uuidEntity = UuidPersistentResourceFixtures.uuidPersistentResource1();

        service.delete(uuidEntity);
        verify(repository).delete(uuidEntity.id());
    }

    @Test
    public void dshouldCallRepositoryDeleteWithResources_whenDeletingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(UuidPersistentResourceFixtures.uuidPersistentResource1().id(), UuidPersistentResourceFixtures.uuidPersistentResource2().id());

        service.delete(uuidEntityIds);
        verify(repository).delete(uuidEntityIds);
    }

    @Test
    public void shouldCallRepositoryDeleteAll_whenDeletingAllResources() {
        service.deleteAll();
        verify(repository).deleteAll();
    }
}
