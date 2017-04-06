package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.mock.UUIDEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UUIDPersistentResourceTest {
    
    private static final Long ID = 1L;
    private static final String UUID = "c4726093-fa44-4b4c-8108-3fdcacffabd6";

    @Test
    public void shouldAssignAnId() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity.id()).isNotNull();
    }

    @Test
    public void shouldAssignAnUuid() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity.uuid()).isNotNull();
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenIdIsNull() {
        new UUIDEntity(null, UUID);
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenUuidIsNull() {
        new UUIDEntity(ID, null);
    }

    @Test
    public void shouldNotBeEqualToOtherTypes() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        Object otherTypeObject = new Object();
        
        assertThat(newUUIDEntity).isNotEqualTo(otherTypeObject);
    }
    
    @Test
    public void shouldNotBeEqualToNull() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isNotEqualTo(null);
    }
    
    @Test
    public void shouldBeEqualToItself() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualToOtherInstanceWithSameUuid() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualSymmetrically() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(newUUIDEntity);
    }
    
    @Test
    public void shouldBeEqualTransitively() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity anotherSameUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(sameUUIDEntity).isEqualTo(anotherSameUUIDEntity);
        assertThat(newUUIDEntity).isEqualTo(anotherSameUUIDEntity);
    }
    
    @Test
    public void shouldNotHaveSameHashCodeWhenNotEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity otherUUIDEntity = new UUIDEntity(ID, "anotherUuid");

        assertThat(newUUIDEntity).isNotEqualTo(otherUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isNotEqualTo(otherUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldHaveSameHashCodeWhenEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);
        UUIDEntity sameUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity).isEqualTo(sameUUIDEntity);
        assertThat(newUUIDEntity.hashCode()).isEqualTo(sameUUIDEntity.hashCode());
    }
    
    @Test
    public void shouldPrintsItsUuid() {
        UUIDEntity newUUIDEntity = new UUIDEntity(ID, UUID);

        assertThat(newUUIDEntity.toString()).contains(newUUIDEntity.uuid());
    }
}
