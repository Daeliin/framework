package com.blebail.components.persistence.resource.repository;

import com.blebail.components.persistence.fake.UuidResourceRepository;
import com.blebail.components.persistence.fixtures.JavaFixtures;
import com.blebail.components.persistence.fixtures.UuidResourceRows;
import com.blebail.components.persistence.library.UuidResourceLibrary;
import com.blebail.components.persistence.sql.BUuidResource;
import com.blebail.components.persistence.sql.QUuidResource;
import com.blebail.components.test.rule.DbFixture;
import com.blebail.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ResourceRepositoryIT {

    @Inject
    private UuidResourceRepository tested;

    @RegisterExtension
    static DbMemory dbMemory = new DbMemory();

    @RegisterExtension
    DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.uuidResources());

    @Test
    void shouldProvideTheIdPath() {
        dbFixture.readOnly();

        assertThat(tested.idPath()).isEqualTo(QUuidResource.uuidResource.uuid);
    }

    @Test
    void shouldProvideTheIdMapping() {
        dbFixture.readOnly();

        assertThat(tested.idMapping()).isNotNull();
    }

    @Test
    void shouldThrowException_whenPersistingNull() {
        dbFixture.readOnly();

        BUuidResource nullUuidEntity = null;

        assertThrows(Exception.class, () -> tested.save(nullUuidEntity));
    }

    @Test
    void shouldPersistAResource() throws Exception {
        BUuidResource newUuuidPersistentResource = new BUuidResource(Instant.now(), "label100", UUID.randomUUID().toString());
        int uuidPersistentResourceCountBeforeCreate = countRows();

        BUuidResource persistedUuidEntity = tested.findOne(tested.save(newUuuidPersistentResource).getUuid()).get();

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    void shouldReturnThePersistedResource() {
        BUuidResource newUuuidPersistentResource = new BUuidResource(Instant.now(), "label100", UUID.randomUUID().toString());

        BUuidResource returnedUuidEntity = tested.save(newUuuidPersistentResource);

        assertThat(returnedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    void shouldPersistResources() throws Exception {
        BUuidResource newUuuidPersistentResource1 = new BUuidResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidResource newUuuidPersistentResource2 = new BUuidResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        int uuidPersistentResourceCountBeforeCreate = countRows();

        tested.save(newUuidEntities);

        int uuidPersistentResourceCountAterCreate = countRows();

        assertThat(uuidPersistentResourceCountAterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    void shouldReturnThePersistedResources() {
        BUuidResource newUuuidPersistentResource1 = new BUuidResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidResource newUuuidPersistentResource2 = new BUuidResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        Collection<BUuidResource> returnedUuidEntities = tested.save(newUuidEntities);

        assertThat(returnedUuidEntities).containsOnly(newUuuidPersistentResource1, newUuuidPersistentResource2);
    }

    @Test
    void shouldUpdateAResource() {
        BUuidResource resourceToUpdate = new BUuidResource(Instant.now(), "newLabel", UuidResourceLibrary.uuidResource1().id());
        tested.save(resourceToUpdate);

        Optional<BUuidResource> updatedResource = tested.findOne(resourceToUpdate.getUuid());

        assertThat(updatedResource).isNotEmpty();
        assertThat(updatedResource.get()).isEqualToComparingFieldByField(resourceToUpdate);
    }

    @Test
    void shouldReturnTheUpdatedResource() {
        BUuidResource resourceToUpdate = new BUuidResource(Instant.now(), "newLabel", UuidResourceLibrary.uuidResource1().id());
        BUuidResource updatedResource = tested.save(resourceToUpdate);

        assertThat(updatedResource).isEqualToComparingFieldByField(resourceToUpdate);
    }

    @Test
    void shouldUpdateResources() {
        BUuidResource resourceToUpdate1 = new BUuidResource(Instant.now(), "newLabel1", UuidResourceLibrary.uuidResource1().id());
        BUuidResource resourceToUpdate2 = new BUuidResource(Instant.now(), "newLabel2", UuidResourceLibrary.uuidResource2().id());
        tested.save(Set.of(resourceToUpdate1, resourceToUpdate2));

        Optional<BUuidResource> updatedResource1 = tested.findOne(resourceToUpdate1.getUuid());
        Optional<BUuidResource> updatedResource2 = tested.findOne(resourceToUpdate2.getUuid());
        Optional<BUuidResource> notUpdatedResource = tested.findOne(UuidResourceRows.uuidResource3().getUuid());

        assertThat(updatedResource1).isNotEmpty();
        assertThat(updatedResource1.get()).isEqualToComparingFieldByField(resourceToUpdate1);

        assertThat(updatedResource2).isNotEmpty();
        assertThat(updatedResource2.get()).isEqualToComparingFieldByField(resourceToUpdate2);

        assertThat(notUpdatedResource).isNotEmpty();
        assertThat(notUpdatedResource.get()).isEqualToComparingFieldByField(UuidResourceRows.uuidResource3());
    }

    @Test
    void shouldReturnUpdatedResources() {
        BUuidResource resourceToUpdate1 = new BUuidResource(Instant.now(), "newLabel1", UuidResourceLibrary.uuidResource1().id());
        BUuidResource resourceToUpdate2 = new BUuidResource(Instant.now(), "newLabel2", UuidResourceLibrary.uuidResource2().id());
        List<BUuidResource> updatedResources = List.copyOf(tested.save(Set.of(resourceToUpdate1, resourceToUpdate2)))
                .stream()
                .sorted(Comparator.comparing(BUuidResource::getLabel))
                .collect(Collectors.toList());

        assertThat(updatedResources.get(0)).isEqualToComparingFieldByField(resourceToUpdate1);
        assertThat(updatedResources.get(1)).isEqualToComparingFieldByField(resourceToUpdate2);
    }

    @Test
    void shouldCheckIfResourceExists() {
        dbFixture.readOnly();

        assertThat(tested.exists(UuidResourceRows.uuidResource1().getUuid())).isTrue();
    }

    @Test
    void shouldCheckIfResourceDoesntExist_whenIdDoesntExist() {
        dbFixture.readOnly();

        assertThat(tested.exists("894984-984")).isFalse();
    }

    @Test
    void shouldCheckIfResourceDoesntExist_whenIdIsNull() {
        dbFixture.readOnly();

        assertThat(tested.exists(null)).isFalse();
    }

    @Test
    void shouldFindResource() {
        dbFixture.readOnly();

        BUuidResource uuidPersistentResource1 = UuidResourceRows.uuidResource1();

        BUuidResource foundUuidEntity = tested.findOne(uuidPersistentResource1.getUuid()).get();

        assertThat(foundUuidEntity).isEqualToComparingFieldByField(UuidResourceRows.uuidResource1());
    }

    @Test
    void shouldReturnEmpty_whenFindingNonExistingResource() {
        dbFixture.readOnly();

        assertThat(tested.findOne("6846984-864684").isPresent()).isFalse();
    }

    @Test
    void shouldReturnEmpty_whenFindingNull() {
        dbFixture.readOnly();

        String nullId = null;

        assertThat(tested.findOne(nullId).isPresent()).isFalse();
    }

    @Test
    void shouldFindResources() {
        dbFixture.readOnly();

        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        Collection<BUuidResource> uuidEntities = tested.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidResourceRows.uuidResource1(), UuidResourceRows.uuidResource2());
    }

    @Test
    void shouldReturnNoResources_whenFindingNonExistingResources() {
        dbFixture.readOnly();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList("68464-684", "684684-444"));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    void shouldReturnNoResources_whenFindingZeroResources() {
        dbFixture.readOnly();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList());

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    void shouldReturnNoResources_whenFindingNulls() {
        dbFixture.readOnly();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList(null, null));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    void shouldReturnOnlyExistingResources_whenFindingEexistingAndNonExistingResources() {
        dbFixture.readOnly();

        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid(), "646444-218");
        Collection<BUuidResource> uuidEntities = tested.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidResourceRows.uuidResource1(), UuidResourceRows.uuidResource2());
    }

    @Test
    void shouldReturnFalse_whenDeletingNonExistingResource() {
        assertThat(tested.delete("96846846-4465")).isFalse();
    }

    @Test
    void shouldReturnFalse_whenDeletingNullId() {
        String nullId = null;

        assertThat(tested.delete(nullId)).isFalse();
    }

    @Test
    void shouldNotDeleteAnyResources_whenDeletingNonExistingResource() throws Exception {
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete("6984684-685648");

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    void shouldNotDeleteAnyResources_whenDeletingNullId() throws Exception {
        String nullId = null;
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(nullId);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    void shouldDeleteAResource() throws Exception {
        int uuidPersistentResourceCountBeforeCreate = countRows();

        tested.delete(UuidResourceRows.uuidResource1().getUuid());

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate - 1);
    }

    @Test
    void shouldReturnTrue_whenDeletingResource() {
        boolean delete = tested.delete(UuidResourceRows.uuidResource1().getUuid());

        assertThat(delete).isTrue();
    }

    @Test
    void shouldDeleteMultipleResources() throws Exception {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - uuidPersistentResourceIds.size());
    }

    @Test
    void shouldReturnTrue_whenDeletingMultipleResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        boolean delete = tested.delete(uuidPersistentResourceIds);

        assertThat(delete).isTrue();
    }


    @Test
    void shouldNotDeleteAnyResources_whenDeletingNonExistingResources() throws Exception {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete);
    }

    @Test
    void shouldReturnFalse_whenNotDeletingAnyResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        boolean delete = tested.delete(uuidPersistentResourceIds);

        assertThat(delete).isFalse();
    }

    @Test
    void shouldDeleteOnlyExistingResources_whenDeletingExistingAndNonExistingResources() throws Exception {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid(), "665321-111");

        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - 2);
    }

    private int countRows() throws Exception {
        return dbMemory.countRows(QUuidResource.uuidResource.getTableName());
    }
}
