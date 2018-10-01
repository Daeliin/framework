package com.daeliin.components.persistence.resource.repository;

import com.daeliin.components.persistence.fake.UuidResourceRepository;
import com.daeliin.components.persistence.fixtures.JavaFixtures;
import com.daeliin.components.persistence.fixtures.UuidResourceRows;
import com.daeliin.components.persistence.sql.BUuidResource;
import com.daeliin.components.persistence.sql.QUuidResource;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ResourceRepositoryIT {

    @Inject
    private UuidResourceRepository tested;

    @RegisterExtension
    public static DbMemory dbMemory = new DbMemory();

    @RegisterExtension
    public DbFixture dbFixture = new DbFixture(dbMemory, JavaFixtures.uuidResources());

    @Test
    public void shouldProvideTheIdPath() {
        dbFixture.noRollback();

        assertThat(tested.idPath()).isEqualTo(QUuidResource.uuidResource.uuid);
    }

    @Test
    public void shouldProvideTheIdMapping() {
        dbFixture.noRollback();

        assertThat(tested.idMapping()).isNotNull();
    }

    @Test
    public void shouldThrowException_whenPersistingNull() {
        dbFixture.noRollback();

        BUuidResource nullUuidEntity = null;

        assertThrows(Exception.class, () -> tested.save(nullUuidEntity));
    }

    @Test
    public void shouldPersistAResource() throws Exception {
        BUuidResource newUuuidPersistentResource = new BUuidResource(Instant.now(), "label100", UUID.randomUUID().toString());
        int uuidPersistentResourceCountBeforeCreate = countRows();

        BUuidResource persistedUuidEntity = tested.findOne(tested.save(newUuuidPersistentResource).getUuid()).get();

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + 1);
        assertThat(persistedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldReturnThePersistedResource() {
        BUuidResource newUuuidPersistentResource = new BUuidResource(Instant.now(), "label100", UUID.randomUUID().toString());

        BUuidResource returnedUuidEntity = tested.save(newUuuidPersistentResource);

        assertThat(returnedUuidEntity).isEqualToComparingFieldByField(newUuuidPersistentResource);
    }

    @Test
    public void shouldPersistResources() throws Exception {
        BUuidResource newUuuidPersistentResource1 = new BUuidResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidResource newUuuidPersistentResource2 = new BUuidResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        int uuidPersistentResourceCountBeforeCreate = countRows();

        tested.save(newUuidEntities);

        int uuidPersistentResourceCountAterCreate = countRows();

        assertThat(uuidPersistentResourceCountAterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate + newUuidEntities.size());
    }

    @Test
    public void shouldReturnThePersistedResources() {
        BUuidResource newUuuidPersistentResource1 = new BUuidResource(Instant.now(), "label101", UUID.randomUUID().toString());
        BUuidResource newUuuidPersistentResource2 = new BUuidResource(Instant.now(), "label102", UUID.randomUUID().toString());
        List<BUuidResource> newUuidEntities = Arrays.asList(newUuuidPersistentResource1, newUuuidPersistentResource2);

        Collection<BUuidResource> returnedUuidEntities = tested.save(newUuidEntities);

        assertThat(returnedUuidEntities).containsOnly(newUuuidPersistentResource1, newUuuidPersistentResource2);
    }

    @Test
    public void shouldCheckIfResourceExists() {
        dbFixture.noRollback();

        assertThat(tested.exists(UuidResourceRows.uuidResource1().getUuid())).isTrue();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdDoesntExist() {
        dbFixture.noRollback();

        assertThat(tested.exists("894984-984")).isFalse();
    }

    @Test
    public void shouldCheckIfResourceDoesntExist_whenIdIsNull() {
        dbFixture.noRollback();

        assertThat(tested.exists(null)).isFalse();
    }

    @Test
    public void shouldFindResource() {
        dbFixture.noRollback();

        BUuidResource uuidPersistentResource1 = UuidResourceRows.uuidResource1();

        BUuidResource foundUuidEntity = tested.findOne(uuidPersistentResource1.getUuid()).get();

        assertThat(foundUuidEntity).isEqualToComparingFieldByField(UuidResourceRows.uuidResource1());
    }

    @Test
    public void shouldReturnEmpty_whenFindingNonExistingResource() {
        dbFixture.noRollback();

        assertThat(tested.findOne("6846984-864684").isPresent()).isFalse();
    }

    @Test
    public void shouldReturnEmpty_whenFindingNull() {
        dbFixture.noRollback();

        String nullId = null;

        assertThat(tested.findOne(nullId).isPresent()).isFalse();
    }

    @Test
    public void shouldFindResources() {
        dbFixture.noRollback();

        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        Collection<BUuidResource> uuidEntities = tested.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidResourceRows.uuidResource1(), UuidResourceRows.uuidResource2());
    }

    @Test
    public void shouldReturnNoResources_whenFindingNonExistingResources() {
        dbFixture.noRollback();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList("68464-684", "684684-444"));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingZeroResources() {
        dbFixture.noRollback();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList());

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnNoResources_whenFindingNulls() {
        dbFixture.noRollback();

        Collection<BUuidResource> uuidEntities = tested.findAll(Arrays.asList(null, null));

        assertThat(uuidEntities).isEmpty();
    }

    @Test
    public void shouldReturnOnlyExistingResources_whenFindingEexistingAndNonExistingResources() {
        dbFixture.noRollback();

        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid(), "646444-218");
        Collection<BUuidResource> uuidEntities = tested.findAll(uuidPersistentResourceIds);

        assertThat(uuidEntities)
                .usingFieldByFieldElementComparator()
                .containsOnly(UuidResourceRows.uuidResource1(), UuidResourceRows.uuidResource2());
    }

    @Test
    public void shouldReturnFalse_whenDeletingNonExistingResource() {
        assertThat(tested.delete("96846846-4465")).isFalse();
    }

    @Test
    public void shouldReturnFalse_whenDeletingNullId() {
        String nullId = null;

        assertThat(tested.delete(nullId)).isFalse();
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNonExistingResource() throws Exception {
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete("6984684-685648");

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNullId() throws Exception {
        String nullId = null;
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(nullId);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountBeforeDelete).isEqualTo(uuidPersistentResourceCountAfterDelete);
    }

    @Test
    public void shouldDeleteAResource() throws Exception {
        int uuidPersistentResourceCountBeforeCreate = countRows();

        tested.delete(UuidResourceRows.uuidResource1().getUuid());

        int uuidPersistentResourceCountAfterCreate = countRows();

        assertThat(uuidPersistentResourceCountAfterCreate).isEqualTo(uuidPersistentResourceCountBeforeCreate - 1);
    }

    @Test
    public void shouldReturnTrue_whenDeletingResource() {
        boolean delete = tested.delete(UuidResourceRows.uuidResource1().getUuid());

        assertThat(delete).isTrue();
    }

    @Test
    public void shouldDeleteMultipleResources() throws Exception {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete - uuidPersistentResourceIds.size());
    }

    @Test
    public void shouldReturnTrue_whenDeletingMultipleResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList(UuidResourceRows.uuidResource1().getUuid(), UuidResourceRows.uuidResource2().getUuid());
        boolean delete = tested.delete(uuidPersistentResourceIds);

        assertThat(delete).isTrue();
    }


    @Test
    public void shouldNotDeleteAnyResources_whenDeletingNonExistingResources() throws Exception {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        int uuidPersistentResourceCountBeforeDelete = countRows();

        tested.delete(uuidPersistentResourceIds);

        int uuidPersistentResourceCountAfterDelete = countRows();

        assertThat(uuidPersistentResourceCountAfterDelete).isEqualTo(uuidPersistentResourceCountBeforeDelete);
    }

    @Test
    public void shouldReturnFalse_whenNotDeletingAnyResources() {
        List<String> uuidPersistentResourceIds = Arrays.asList("1111-222", "313213-321321");
        boolean delete = tested.delete(uuidPersistentResourceIds);

        assertThat(delete).isFalse();
    }

    @Test
    public void shouldDeleteOnlyExistingResources_whenDeletingExistingAndNonExistingResources() throws Exception {
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
