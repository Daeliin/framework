package com.daeliin.components.core.resource.service;

import com.daeliin.components.core.Application;
import com.daeliin.components.core.exception.PersistentResourceNotFoundException;
import com.daeliin.components.core.fake.UuidPersistentResource;
import com.daeliin.components.core.fake.UuidPersistentResourceRepository;
import com.daeliin.components.core.fake.UuidPersistentResourceService;
import com.daeliin.components.core.library.UuidPersistentResourceLibrary;
import com.daeliin.components.core.sql.BUuidPersistentResource;
import com.daeliin.components.domain.pagination.Page;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = Application.class)
public class ResourceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Mock
    private UuidPersistentResourceRepository repositoryMock;

    @InjectMocks
    private UuidPersistentResourceService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnResource_whenCreatingResource() {
        UuidPersistentResource newUuuidEntity = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label100");

        doReturn(service.map(newUuuidEntity)).when(repositoryMock).save(any(BUuidPersistentResource.class));

        UuidPersistentResource createdUuidEntity = service.create(newUuuidEntity);

        verify(repositoryMock).save(any(BUuidPersistentResource.class));
        assertThat(createdUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnsResources_whenCreatingResources() {
        UuidPersistentResource newUuidEntity1 = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label101");
        UuidPersistentResource newUuidEntity2 = new UuidPersistentResource(UUID.randomUUID().toString(), LocalDateTime.now(), "label102");

        List<UuidPersistentResource> newUuidEntites = Arrays.asList(newUuidEntity1, newUuidEntity2);

        doReturn(service.map(newUuidEntites)).when(repositoryMock).save(any(Collection.class));

        Collection<UuidPersistentResource> createdUuidEntities = service.create(newUuidEntites);

        verify(repositoryMock).save(any(Collection.class));

        assertThat(createdUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }

    @Test
    public void shouldCheckThatNullDoesntExist() {
        assertThat(service.exists(null)).isFalse();
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdExists() {
        String existingUuidEntityId = UuidPersistentResourceLibrary.uuidPersistentResource1().id();

        doReturn(true).when(repositoryMock).exists(existingUuidEntityId);

        assertThat(service.exists(existingUuidEntityId)).isTrue();
        verify(repositoryMock).exists(existingUuidEntityId);
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdDoesntExist() {
        doReturn(false).when(repositoryMock).exists("654684-64684");

        assertThat(service.exists("654684-64684")).isFalse();
        verify(repositoryMock).exists("654684-64684");
    }

    @Test
    public void shouldCallRepositoryCountAndReturnTheSameResult() {
        doReturn(5L).when(repositoryMock).count();

        assertThat(service.count()).isEqualTo(5L);
        verify(repositoryMock).count();
    }

    @Test(expected = PersistentResourceNotFoundException.class)
    public void shouldThrowPersistentResourceNotFoundException_whenIdIsNull() {
        service.findOne(null);
    }

    @Test
    public void shouldThrowPersistentResourceNotFoundException_whenResourceIdDoesntExist() {
        doThrow(new PersistentResourceNotFoundException("")).when(repositoryMock).findOne("654684-64684");

        try {
             service.findOne("654684-64684");
        } catch (PersistentResourceNotFoundException e) {
            verify(repositoryMock).findOne("654684-64684");
            return;
        }

        fail();
    }

    @Test
    public void shouldCallRepositoryFindOneAndReturnsResource_whenFindingResource() {
        UuidPersistentResource existingUuidEntity = UuidPersistentResourceLibrary.uuidPersistentResource1();

        doReturn(service.map(existingUuidEntity)).when(repositoryMock).findOne(existingUuidEntity.id());

        UuidPersistentResource foundUuidEntity = service.findOne(existingUuidEntity.id());

        verify(repositoryMock).findOne(existingUuidEntity.id());
        assertThat(foundUuidEntity).isEqualTo(existingUuidEntity);
    }

    @Test
    public void shouldCallRepositoryFindAllAndReturnsAllResources_whenFindingAllResources() {
        Collection<UuidPersistentResource> allUuidEntities = Arrays.asList(
                UuidPersistentResourceLibrary.uuidPersistentResource1(),
                UuidPersistentResourceLibrary.uuidPersistentResource2(),
                UuidPersistentResourceLibrary.uuidPersistentResource3(),
                UuidPersistentResourceLibrary.uuidPersistentResource4());

        doReturn(service.map(allUuidEntities)).when(repositoryMock).findAll();

        Collection<UuidPersistentResource> foundUuidEntities = service.findAll();

        verify(repositoryMock).findAll();
        assertThat(foundUuidEntities).containsAll(allUuidEntities);
    }

    @Test
    public void shouldCallRepositoryFindAllWithPageRequest_whenFindingAllResoucesWithPageRequest() {
        PageRequest pageRequest = new PageRequest(1, 10, Sets.newHashSet(new Sort("uuid", Sort.Direction.ASC)));

        Page<BUuidPersistentResource> pageFake =
                new Page<>(Arrays.asList(service.map(UuidPersistentResourceLibrary.uuidPersistentResource1())), 1, 1);

        doReturn(pageFake).when(repositoryMock).findAll(pageRequest);

        service.findAll(pageRequest);
        verify(repositoryMock).findAll(pageRequest);
    }

    @Test
    public void shouldCallRepositoryFindAllWithResourceIds_whenFindingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(
                UuidPersistentResourceLibrary.uuidPersistentResource1().id(),
                UuidPersistentResourceLibrary.uuidPersistentResource2().id());

        service.findAll(uuidEntityIds);
        verify(repositoryMock).findAll(uuidEntityIds);
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
        UuidPersistentResource exitingUuidEntity = UuidPersistentResourceLibrary.uuidPersistentResource1();

        doReturn(true).when(repositoryMock).exists(exitingUuidEntity.id());
        doReturn(service.map(exitingUuidEntity)).when(repositoryMock).save(any(BUuidPersistentResource.class));

        UuidPersistentResource updatedUuidEntity = service.update(exitingUuidEntity);

        verify(repositoryMock).save(any(BUuidPersistentResource.class));
        assertThat(updatedUuidEntity).isEqualTo(exitingUuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveWithMultipleResources_whenUpdatingResources() {
        Collection<UuidPersistentResource> existingUuidEntities = Arrays.asList(
                UuidPersistentResourceLibrary.uuidPersistentResource1(),
                UuidPersistentResourceLibrary.uuidPersistentResource2());

        doReturn(service.map(existingUuidEntities)).when(repositoryMock).save(any(Collection.class));

        Collection<UuidPersistentResource> updatedUuidEntities = service.update(existingUuidEntities);

        verify(repositoryMock).save(any(Collection.class));
        assertThat(updatedUuidEntities).containsAll(existingUuidEntities);
    }

    @Test
    public void shouldCallRepositoryDelete_whenDeletingResource() {
        service.delete(UuidPersistentResourceLibrary.uuidPersistentResource1().id());
        verify(repositoryMock).delete(UuidPersistentResourceLibrary.uuidPersistentResource1().id());
    }

    @Test
    public void shouldReturnFalse_whenDeletingNoResources() {
        assertThat(service.delete(Arrays.asList())).isFalse();

    }

    @Test
    public void shouldCallRepositoryDeleteWithResourceIds_whenDeletingResources() {
        UuidPersistentResource uuidEntity1 = UuidPersistentResourceLibrary.uuidPersistentResource1();
        UuidPersistentResource uuidEntity2 = UuidPersistentResourceLibrary.uuidPersistentResource2();

        service.delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));

        verify(repositoryMock).delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));
    }

    @Test
    public void shouldCallRepositoryDeleteWithResource_whenDeletingResource() {
        UuidPersistentResource uuidEntity = UuidPersistentResourceLibrary.uuidPersistentResource1();

        service.delete(uuidEntity);
        verify(repositoryMock).delete(uuidEntity.id());
    }

    @Test
    public void dshouldCallRepositoryDeleteWithResources_whenDeletingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(
                UuidPersistentResourceLibrary.uuidPersistentResource1().id(),
                UuidPersistentResourceLibrary.uuidPersistentResource2().id());

        service.delete(uuidEntityIds);
        verify(repositoryMock).delete(uuidEntityIds);
    }

    @Test
    public void shouldCallRepositoryDeleteAll_whenDeletingAllResources() {
        service.deleteAll();
        verify(repositoryMock).deleteAll();
    }
}
