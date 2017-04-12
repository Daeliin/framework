package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.fake.UUIDEntity;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class UUIDPersistentResourceTest {
    
    private static final Long ID = 1L;
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();

    @Test
    public void shouldAssignADefaultCreationDate() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, null);

        assertThat(newUUIDEntity.creationDate()).isNotNull();
    }

    @Test
    public void shouldAssignAnId() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity.id()).isEqualTo(ID);
    }

    @Test
    public void shouldAssignAnUuid() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity.uuid()).isEqualTo(UUID);
    }

    @Test
    public void shouldAssignACreationDate() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity.creationDate()).isEqualTo(CREATION_DATE);
    }


    @Test(expected = Exception.class)
    public void shouldThrowException_whenIdIsNull() {
        new UUIDEntity(null, UUID, CREATION_DATE);
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenUuidIsNull() {
        new UUIDEntity(ID, null, CREATION_DATE);
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity anotherSameUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCode_whenNotEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity otherUUIDEntity = new UUIDEntity(ID, "anotherUuid", CREATION_DATE);

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCode_whenEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsIdUuidAndCreationDate() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID, CREATION_DATE);

        assertThat(newUUIDEntity.toString()).contains(String.valueOf(newUUIDEntity.id()), newUUIDEntity.uuid(), newUUIDEntity.creationDate.toString());
    }
}
