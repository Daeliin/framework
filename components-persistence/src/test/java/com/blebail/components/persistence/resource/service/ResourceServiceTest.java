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

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResourceServiceTest {

    private UuidResourceConversion conversion = new UuidResourceConversion();

    private UuidResourceRepository repositoryMock = mock(UuidResourceRepository.class);

    private UuidResourceService tested;

    @BeforeEach
    public void setUp() {
        tested = new UuidResourceService(repositoryMock);
    }

    @Test
    public void shouldCallRepositoryCountWithPredicateAndReturnTheSameResult_whenCountingWithPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id());

        doReturn(1L).when(repositoryMock).count(predicate);

        assertThat(tested.count(predicate)).isEqualTo(1L);
        verify(repositoryMock).count(predicate);
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
    public void shouldCallRepositoryDeleteWithPredicate_whenDeletingResourcesWithPredicate() {
        Predicate predicate = QUuidResource.uuidResource.uuid.eq(UuidResourceLibrary.uuidResource1().id());

        tested.delete(predicate);
        verify(repositoryMock).delete(predicate);
    }
}
