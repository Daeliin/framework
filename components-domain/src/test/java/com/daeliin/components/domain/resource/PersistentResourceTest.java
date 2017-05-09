package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.fake.UuidPersistentResource;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class PersistentResourceTest {
    
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();

    @Test
    public void shouldAssignADefaultCreationDate() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, null);

        assertThat(newUUIDEntity.creationDate()).isNotNull();
    }

    @Test
    public void shouldAssignAnUuid() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.id()).isEqualTo(UUID);
    }

    @Test
    public void shouldAssignACreationDate() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.creationDate()).isEqualTo(CREATION_DATE);
    }


    @Test(expected = Exception.class)
    public void shouldThrowException_whenUuidIsNull() {
        new UuidPersistentResource(null, CREATION_DATE);
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource sameUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource sameUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource sameUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource anotherSameUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCode_whenNotEqual() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource otherUUIDEntity = new UuidPersistentResource("anotherUuid", CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCode_whenEqual() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);
        UuidPersistentResource sameUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsUuidAndCreationDate() {
        UuidPersistentResource newUUIDEntity = new UuidPersistentResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.toString()).contains(newUUIDEntity.id(), newUUIDEntity.creationDate().toString());
    }
}
