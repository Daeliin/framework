package com.blebail.components.persistence.resource.service;

import com.blebail.components.persistence.fake.UuidCachedBaseService;
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
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CachedBaseServiceTest {

    private UuidResourceConversion conversion = new UuidResourceConversion();

    private UuidResourceRepository repositoryMock = mock(UuidResourceRepository.class);

    private UuidCachedBaseService tested;

    @BeforeEach
    public void setUp() {
        tested = new UuidCachedBaseService(repositoryMock);
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
    public void shouldReturnTrue_whenResourceIdExists() {
        String existingUuidEntityId = UuidResourceLibrary.uuidResource1().id();

        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        assertThat(tested.exists(existingUuidEntityId)).isTrue();
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldReturnFalse_whenResourceIdDoesntExist() {
        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        assertThat(tested.exists("654684-64684")).isFalse();
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldReturnTheResourceCount() {
        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        assertThat(tested.count()).isEqualTo(1L);
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldThrowException_whenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> tested.findOne(null));
    }

    @Test
    public void shouldThrowException_whenResourceIdDoesntExist() {
        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        assertThrows(NoSuchElementException.class, () -> tested.findOne("654684-64684"));
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldReturnResource_whenFindingResource() {
        UuidResource existingUuidEntity = UuidResourceLibrary.uuidResource1();

        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        UuidResource foundUuidEntity = tested.findOne(existingUuidEntity.id());

        assertThat(foundUuidEntity).isEqualTo(existingUuidEntity);
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldNotHitTheDatabaseTwiceAndReturnResource_whenFindingResourceTwice() {
        UuidResource existingUuidEntity = UuidResourceLibrary.uuidResource1();

        doReturn(Set.of(conversion.to(UuidResourceLibrary.uuidResource1()))).when(repositoryMock).findAll();

        tested.findOne(existingUuidEntity.id());
        UuidResource foundUuidEntity = tested.findOne(existingUuidEntity.id());

        assertThat(foundUuidEntity).isEqualTo(existingUuidEntity);
        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    public void shouldReturnAllResources_whenFindingAllResources() {
        Collection<UuidResource> allUuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2(),
                UuidResourceLibrary.uuidResource3(),
                UuidResourceLibrary.uuidResource4());

        doReturn(conversion.to(allUuidEntities)).when(repositoryMock).findAll();

        Collection<UuidResource> foundUuidEntities = tested.findAll();

        assertThat(foundUuidEntities).containsAll(allUuidEntities);
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldNotHitTheDatabaseTwiceAndReturnAllResources_whenFindingAllResourcesTwice() {
        Collection<UuidResource> allUuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2(),
                UuidResourceLibrary.uuidResource3(),
                UuidResourceLibrary.uuidResource4());

        doReturn(conversion.to(allUuidEntities)).when(repositoryMock).findAll();

        tested.findAll();
        Collection<UuidResource> foundUuidEntities = tested.findAll();

        assertThat(foundUuidEntities).containsAll(allUuidEntities);
        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    public void shouldReturnResources_whenFindingResources() {
        Collection<UuidResource> uuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2());

        Collection<String> uuidEntityIds = Arrays.asList(
                UuidResourceLibrary.uuidResource1().id(),
                UuidResourceLibrary.uuidResource2().id());

        doReturn(conversion.to(uuidEntities)).when(repositoryMock).findAll();

        Collection<UuidResource> returnedUuidEntities = tested.findAll(uuidEntityIds);

        assertThat(returnedUuidEntities).containsOnlyElementsOf(uuidEntities);

        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldNotHitTheDatabaseTwiceAndReturnResources_whenFindingResourcesTwice() {
        Collection<UuidResource> uuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2());

        Collection<String> uuidEntityIds = Arrays.asList(
                UuidResourceLibrary.uuidResource1().id(),
                UuidResourceLibrary.uuidResource2().id());

        doReturn(conversion.to(uuidEntities)).when(repositoryMock).findAll();

        tested.findAll(uuidEntityIds);
        Collection<UuidResource> returnedUuidEntities = tested.findAll(uuidEntityIds);

        assertThat(returnedUuidEntities).containsOnlyElementsOf(uuidEntities);

        verify(repositoryMock, times(1)).findAll();
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
        doReturn(Set.of(conversion.to(exitingUuidEntity))).when(repositoryMock).findAll();

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
        doReturn(conversion.to(existingUuidEntities)).when(repositoryMock).findAll();

        Collection<UuidResource> updatedUuidEntities = tested.update(existingUuidEntities);

        verify(repositoryMock).save(any(Collection.class));
        assertThat(updatedUuidEntities).containsAll(existingUuidEntities);
    }

    @Test
    public void shouldCallRepositoryDelete_whenDeletingResource() {
        UuidResource existingUuidEntity = UuidResourceLibrary.uuidResource1();

        tested.delete(existingUuidEntity.id());
        verify(repositoryMock).delete(existingUuidEntity.id());
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
    public void shouldCallRepositoryDeleteAllAbdReturn_whenDeletingAllResources() {
        tested.deleteAll();
        verify(repositoryMock).deleteAll();
    }
}

