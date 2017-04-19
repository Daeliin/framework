package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.fake.UuidPersistenceResource;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class PersistentResourceTest {
    
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();

    @Test
    public void shouldAssignADefaultCreationDate() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, null);

        assertThat(newUUIDEntity.creationDate()).isNotNull();
    }

    @Test
    public void shouldAssignAnUuid() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.id()).isEqualTo(UUID);
    }

    @Test
    public void shouldAssignACreationDate() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.creationDate()).isEqualTo(CREATION_DATE);
    }


    @Test(expected = Exception.class)
    public void shouldThrowException_whenUuidIsNull() {
        new UuidPersistenceResource(null, CREATION_DATE);
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource sameUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource sameUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource sameUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource anotherSameUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCode_whenNotEqual() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource otherUUIDEntity = new UuidPersistenceResource("anotherUuid", CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCode_whenEqual() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);
        UuidPersistenceResource sameUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsUuidAndCreationDate() {
        UuidPersistenceResource newUUIDEntity = new UuidPersistenceResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.toString()).contains(newUUIDEntity.id(), newUUIDEntity.creationDate.toString());
    }
}
