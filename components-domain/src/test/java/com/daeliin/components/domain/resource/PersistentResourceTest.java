package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.fake.FakePersistenceResource;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class PersistentResourceTest {
    
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();

    @Test
    public void shouldAssignADefaultCreationDate() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, null);

        assertThat(newUUIDEntity.creationDate()).isNotNull();
    }

    @Test
    public void shouldAssignAnUuid() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.id()).isEqualTo(UUID);
    }

    @Test
    public void shouldAssignACreationDate() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.creationDate()).isEqualTo(CREATION_DATE);
    }


    @Test(expected = Exception.class)
    public void shouldThrowException_whenUuidIsNull() {
        new FakePersistenceResource(null, CREATION_DATE);
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource sameUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource sameUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource sameUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource anotherSameUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCode_whenNotEqual() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource otherUUIDEntity = new FakePersistenceResource("anotherUuid", CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCode_whenEqual() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);
        FakePersistenceResource sameUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsUuidAndCreationDate() {
        FakePersistenceResource newUUIDEntity = new FakePersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.toString()).contains(newUUIDEntity.id(), newUUIDEntity.creationDate.toString());
    }
}
