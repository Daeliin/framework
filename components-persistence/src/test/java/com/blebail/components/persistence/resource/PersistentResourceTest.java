package com.blebail.components.persistence.resource;

import com.blebail.components.persistence.fake.UuidResource;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

;


public class PersistentResourceTest {
    
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    private static final Instant CREATION_DATE = Instant.now();

    @Test
    public void shouldThrowException_whenCreationDateIsNull() {
        assertThrows(Exception.class, () -> new UuidResource(UUID, null));
    }

    @Test
    public void shouldAssignADefaultCreationDate() {
        UuidResource newUUIDEntity = new UuidResource(UUID);

        assertThat(newUUIDEntity.creationDate()).isNotNull();
    }

    @Test
    public void shouldAssignAnUuid() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.id()).isEqualTo(UUID);
    }

    @Test
    public void shouldAssignACreationDate() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.creationDate()).isEqualTo(CREATION_DATE);
    }


    @Test
    public void shouldThrowException_whenUuidIsNull() {
        assertThrows(Exception.class, () -> new UuidResource(null, CREATION_DATE));
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource sameUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource sameUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource sameUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource anotherSameUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCode_whenNotEqual() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource otherUUIDEntity = new UuidResource("anotherUuid", CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCode_whenEqual() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);
        UuidResource sameUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsUuidAndCreationDate() {
        UuidResource newUUIDEntity = new UuidResource(UUID, CREATION_DATE);

        assertThat(newUUIDEntity.toString()).contains(newUUIDEntity.id(), newUUIDEntity.creationDate().toString());
    }
}
