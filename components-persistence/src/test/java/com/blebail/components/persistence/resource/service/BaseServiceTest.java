package com.blebail.components.persistence.resource.service;

import com.blebail.components.persistence.fake.UuidBaseService;
import com.blebail.components.persistence.fake.UuidResource;
import com.blebail.components.persistence.fake.UuidResourceConversion;
import com.blebail.components.persistence.fake.UuidResourceRepository;
import com.blebail.components.persistence.library.UuidResourceLibrary;
import com.blebail.components.persistence.sql.BUuidResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaseServiceTest {

    private UuidResourceConversion conversion = new UuidResourceConversion();

    private UuidResourceRepository repositoryMock = mock(UuidResourceRepository.class);

    private UuidBaseService tested;

    @BeforeEach
    public void setUp() {
        tested = new UuidBaseService(repositoryMock);
    }

    @Test
    public void shouldThrowException_whenCreatingResourceWithAlreadyExistingId() {
        UuidResource alreadyExistingUuidPersistentResource = UuidResourceLibrary.uuidResource1();

        doReturn(true).when(repositoryMock).exists(alreadyExistingUuidPersistentResource.id());

        assertThrows(IllegalStateException.class, () -> tested.create(UuidResourceLibrary.uuidResource1()));
    }

    @Test
    public void shouldCallRepositorySaveAndReturnResource_whenCreatingResource() {
        UuidResource newUuuidEntity = new UuidResource(UUID.randomUUID().toString(), Instant.now(), "label100");

        doReturn(conversion.to(newUuuidEntity)).when(repositoryMock).save(any(BUuidResource.class));

        UuidResource createdUuidEntity = tested.create(newUuuidEntity);

        verify(repositoryMock).save(any(BUuidResource.class));
        assertThat(createdUuidEntity).isEqualTo(newUuuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveAndReturnResources_whenCreatingResources() {
        UuidResource newUuidEntity1 = new UuidResource(UUID.randomUUID().toString(), Instant.now(), "label101");
        UuidResource newUuidEntity2 = new UuidResource(UUID.randomUUID().toString(), Instant.now(), "label102");

        List<UuidResource> newUuidEntites = Arrays.asList(newUuidEntity1, newUuidEntity2);

        doReturn(conversion.to(newUuidEntites)).when(repositoryMock).save(any(Collection.class));

        Collection<UuidResource> createdUuidEntities = tested.create(newUuidEntites);

        verify(repositoryMock).save(any(Collection.class));

        assertThat(createdUuidEntities).containsOnly(newUuidEntity1, newUuidEntity2);
    }

    @Test
    public void shouldCheckThatNullDoesntExist() {
        assertThat(tested.exists(null)).isFalse();
    }

    @Test
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdExists() {
        String existingUuidEntityId = UuidResourceLibrary.uuidResource1().id();

        doReturn(true).when(repositoryMock).exists(existingUuidEntityId);

        assertThat(tested.exists(existingUuidEntityId)).isTrue();
        verify(repositoryMock).exists(existingUuidEntityId);
    }

    @Test
    public void shouldCallRepositoryExistAndReturnFalse_whenResourceIdDoesntExist() {
        doReturn(false).when(repositoryMock).exists("654684-64684");

        assertThat(tested.exists("654684-64684")).isFalse();
        verify(repositoryMock).exists("654684-64684");
    }

    @Test
    public void shouldCallRepositoryCountAndReturnTheSameResult() {
        doReturn(5L).when(repositoryMock).count();

        assertThat(tested.count()).isEqualTo(5L);
        verify(repositoryMock).count();
    }

    @Test
    public void shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> tested.findOne(null));
    }

    @Test
    public void shouldThrowException_whenResourceIdDoesntExist() {
        doThrow(new NoSuchElementException("")).when(repositoryMock).findOne("654684-64684");

        try {
             tested.findOne("654684-64684");
        } catch (NoSuchElementException e) {
            verify(repositoryMock).findOne("654684-64684");
            return;
        }

        fail();
    }

    @Test
    public void shouldCallRepositoryFindOneAndReturnResource_whenFindingResource() {
        UuidResource existingUuidEntity = UuidResourceLibrary.uuidResource1();

        doReturn(Optional.of(conversion.to(existingUuidEntity))).when(repositoryMock).findOne(existingUuidEntity.id());

        UuidResource foundUuidEntity = tested.findOne(existingUuidEntity.id());

        verify(repositoryMock).findOne(existingUuidEntity.id());
        assertThat(foundUuidEntity).isEqualTo(existingUuidEntity);
    }

    @Test
    public void shouldCallRepositoryFindAllAndReturnAllResources_whenFindingAllResources() {
        Collection<UuidResource> allUuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2(),
                UuidResourceLibrary.uuidResource3(),
                UuidResourceLibrary.uuidResource4());

        doReturn(conversion.to(allUuidEntities)).when(repositoryMock).findAll();

        Collection<UuidResource> foundUuidEntities = tested.findAll();

        verify(repositoryMock).findAll();
        assertThat(foundUuidEntities).containsAll(allUuidEntities);
    }

    @Test
    public void shouldCallRepositoryFindAllWithResourceIds_whenFindingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(
                UuidResourceLibrary.uuidResource1().id(),
                UuidResourceLibrary.uuidResource2().id());

        tested.findAll(uuidEntityIds);
        verify(repositoryMock).findAll(uuidEntityIds);
    }

    @Test
    public void shouldThrowException_whenUpdatingNullResource() {
        UuidResource nullUuidEntity = null;

        assertThrows(IllegalArgumentException.class, () -> tested.update(nullUuidEntity));
    }

    @Test
    public void shouldThrowException_whenUpdatingNonExistingResource() {
        UuidResource nonExistingUuidEntity = new UuidResource("654684-64684", Instant.now(), "label-1");

        assertThrows(NoSuchElementException.class, () -> tested.update(nonExistingUuidEntity));
    }

    @Test
    public void shouldCallRepositorySaveWithResourceAndReturnResource_whenUpdatingResource() {
        UuidResource exitingUuidEntity = UuidResourceLibrary.uuidResource1();

        doReturn(true).when(repositoryMock).exists(exitingUuidEntity.id());
        doReturn(conversion.to(exitingUuidEntity)).when(repositoryMock).save(any(BUuidResource.class));

        UuidResource updatedUuidEntity = tested.update(exitingUuidEntity);

        verify(repositoryMock).save(any(BUuidResource.class));
        assertThat(updatedUuidEntity).isEqualTo(exitingUuidEntity);
    }

    @Test
    public void shouldCallRepositorySaveWithMultipleResources_whenUpdatingResources() {
        Collection<UuidResource> existingUuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2());

        doReturn(conversion.to(existingUuidEntities)).when(repositoryMock).save(any(Collection.class));

        Collection<UuidResource> updatedUuidEntities = tested.update(existingUuidEntities);

        verify(repositoryMock).save(any(Collection.class));
        assertThat(updatedUuidEntities).containsAll(existingUuidEntities);
    }

    @Test
    public void shouldCallRepositoryDelete_whenDeletingResource() {
        tested.delete(UuidResourceLibrary.uuidResource1().id());
        verify(repositoryMock).delete(UuidResourceLibrary.uuidResource1().id());
    }

    @Test
    public void shouldReturnFalse_whenDeletingNoResources() {
        assertThat(tested.delete(Arrays.asList())).isFalse();

    }

    @Test
    public void shouldCallRepositoryDeleteWithResourceIds_whenDeletingResources() {
        UuidResource uuidEntity1 = UuidResourceLibrary.uuidResource1();
        UuidResource uuidEntity2 = UuidResourceLibrary.uuidResource2();

        tested.delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));

        verify(repositoryMock).delete(Arrays.asList(uuidEntity1.id(), uuidEntity2.id()));
    }

    @Test
    public void shouldCallRepositoryDeleteWithResource_whenDeletingResource() {
        UuidResource uuidEntity = UuidResourceLibrary.uuidResource1();

        tested.delete(uuidEntity);
        verify(repositoryMock).delete(uuidEntity.id());
    }

    @Test
    public void shouldCallRepositoryDeleteWithResources_whenDeletingResources() {
        Collection<String> uuidEntityIds = Arrays.asList(
                UuidResourceLibrary.uuidResource1().id(),
                UuidResourceLibrary.uuidResource2().id());

        tested.delete(uuidEntityIds);
        verify(repositoryMock).delete(uuidEntityIds);
    }

    @Test
    public void shouldCallRepositoryDeleteAll_whenDeletingAllResources() {
        tested.deleteAll();
        verify(repositoryMock).deleteAll();
    }
}
