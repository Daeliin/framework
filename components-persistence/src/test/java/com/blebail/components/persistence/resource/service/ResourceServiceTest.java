package com.blebail.components.persistence.resource.service;

import com.blebail.components.core.pagination.Page;
import com.blebail.components.core.pagination.PageRequest;
import com.blebail.components.core.pagination.Sort;
import com.blebail.components.persistence.fake.UuidResource;
import com.blebail.components.persistence.fake.UuidResourceConversion;
import com.blebail.components.persistence.fake.UuidResourceRepository;
import com.blebail.components.persistence.fake.UuidResourceService;
import com.blebail.components.persistence.library.UuidResourceLibrary;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ResourceServiceTest {

    private UuidResourceConversion conversion = new UuidResourceConversion();

    private UuidResourceRepository repositoryMock = mock(UuidResourceRepository.class);

    private UuidResourceService tested;

    @BeforeEach
    public void setUp() {
        tested = new UuidResourceService(repositoryMock);
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
    public void shouldCallRepositoryExistAndReturnTrue_whenResourceIdDoesntExist() {
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
    public void shouldCallRepositoryCountWithPredicateAndReturnTheSameResult_whenCountingWithPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id());

        doReturn(1L).when(repositoryMock).count(predicate);

        assertThat(tested.count(predicate)).isEqualTo(1L);
        verify(repositoryMock).count(predicate);
    }

    @Test
    public void shouldThrowException_whenIdIsNull() {
        String nullId = null;

        assertThrows(NoSuchElementException.class, () -> tested.findOne(nullId));
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
    public void shouldCallRepositoryFindOneWithPredicateAndReturnEmpty_whenNoResourceMatchesPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq("nonExistingId");

        doReturn(Optional.empty()).when(repositoryMock).findOne(predicate);

        Optional<UuidResource> foundUuidEntity = tested.findOne(predicate);

        verify(repositoryMock).findOne(predicate);
        assertThat(foundUuidEntity).isEmpty();
    }

    @Test
    public void shouldCallRepositoryFindOneWithPredicateAndReturnResource_whenFindingResourceWithPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id());
        UuidResource existingUuidEntity = UuidResourceLibrary.uuidResource1();

        doReturn(Optional.of(conversion.to(existingUuidEntity))).when(repositoryMock).findOne(predicate);

        Optional<UuidResource> foundUuidEntity = tested.findOne(predicate);
    
        verify(repositoryMock).findOne(predicate);
        assertThat(foundUuidEntity).isNotEmpty();
        assertThat(foundUuidEntity.get()).isEqualTo(existingUuidEntity);
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
    public void shouldCallRepositoryFindAllWithPredicateAndReturnResources_whenFindingResourcesWithPredicate() {
        Predicate predicate =
                QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id())
                .or(QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource2().id()));

        Collection<UuidResource> existingUuidEntities = Arrays.asList(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2());

        doReturn(conversion.to(existingUuidEntities)).when(repositoryMock).findAll(predicate);

        Collection<UuidResource> foundUuidEntities = tested.findAll(predicate);

        verify(repositoryMock).findAll(predicate);
        assertThat(foundUuidEntities).containsOnly(
                UuidResourceLibrary.uuidResource1(),
                UuidResourceLibrary.uuidResource2());
    }
    
    @Test
    public void shouldCallRepositoryFindAllWithPageRequest_whenFindingAllResoucesWithPageRequest() {
        PageRequest pageRequest = new PageRequest(1, 10, Sets.newLinkedHashSet(Arrays.asList(new Sort("uuid", Sort.Direction.ASC))));

        Page<BUuidResource> pageFake =
                new Page<>(Arrays.asList(conversion.to(UuidResourceLibrary.uuidResource1())), 1, 1);

        doReturn(pageFake).when(repositoryMock).findAll(pageRequest);

        tested.findAll(pageRequest);
        verify(repositoryMock).findAll(pageRequest);
    }

    @Test
    public void shouldCallRepositoryFindAllWithPredicateAndPageRequest_whenFindingAllResoucesWithPredicateAndPageRequest() {
        PageRequest pageRequest = new PageRequest(1, 10, Sets.newLinkedHashSet(Arrays.asList(new Sort("uuid", Sort.Direction.ASC))));
        Predicate predicate = QUuidResource.uuidResource.uuid.isNotNull();

        Page<BUuidResource> pageFake =
                new Page<>(Arrays.asList(conversion.to(UuidResourceLibrary.uuidResource1())), 1, 1);

        doReturn(pageFake).when(repositoryMock).findAll(predicate, pageRequest);

        tested.findAll(predicate, pageRequest);
        verify(repositoryMock).findAll(predicate, pageRequest);
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

        assertThrows(NoSuchElementException.class, () -> tested.update(nullUuidEntity));
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
    public void shouldCallRepositoryDeleteWithPredicate_whenDeletingResourcesWithPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id());

        tested.delete(predicate);
        verify(repositoryMock).delete(predicate);
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
