package com.daeliin.components.domain.resource;

import com.daeliin.components.domain.resource.mock.UUIDEntity;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class UUIDPersistentResourceTest {
    
    private static final String UUID_STRING = "c4726093-fa44-4b4c-8108-3fdcacffabd6";
    
    @Test
    public void new_defaultConstructor_assignsAnUUID() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        assertNotNull(newUUIDEntity.getUuid());
    }
    
    @Test
    public void new_defaultConstructor_assignsUUIDToId() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        try {
            UUID.fromString(newUUIDEntity.getUuid());
        } catch(IllegalArgumentException e) {
            fail();
        }
    }
    
    @Test
    public void equals_otherType_returnsFalse() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        Object otherTypeObject = new Object();
        
        assertNotEquals(newUUIDEntity, otherTypeObject);
    }
    
    @Test
    public void equals_null_returnsFalse() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        assertNotEquals(newUUIDEntity, null);
    }
    
    @Test
    public void equals_itself_returnsTrue() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        assertEquals(newUUIDEntity, newUUIDEntity);
    }
    
    @Test
    public void equals_otherInstanceWithSameId_returnsTrue() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        UUIDEntity sameUUIDEntity = new UUIDEntity();
        
        newUUIDEntity.setUUID(UUID_STRING);
        sameUUIDEntity.setUUID(UUID_STRING);
        
        assertEquals(newUUIDEntity, sameUUIDEntity);
    }
    
    @Test
    public void equals_isSymetric() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        UUIDEntity sameUUIDEntity = new UUIDEntity();
        
        newUUIDEntity.setUUID(UUID_STRING);
        sameUUIDEntity.setUUID(UUID_STRING);
        
        assertEquals(newUUIDEntity, sameUUIDEntity);
        assertEquals(sameUUIDEntity, newUUIDEntity);
    }
    
    @Test
    public void equals_isTransitive() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        UUIDEntity sameUUIDEntity = new UUIDEntity();
        UUIDEntity anotherSameUUIDEntity = new UUIDEntity();
        
        newUUIDEntity.setUUID(UUID_STRING);
        sameUUIDEntity.setUUID(UUID_STRING);
        anotherSameUUIDEntity.setUUID(UUID_STRING);
        
        assertEquals(newUUIDEntity, sameUUIDEntity);
        assertEquals(sameUUIDEntity, anotherSameUUIDEntity);
        assertEquals(newUUIDEntity, anotherSameUUIDEntity);
    }
    
    @Test
    public void hashCode_areNotTheSameWhenNotEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        UUIDEntity otherUUIDEntity = new UUIDEntity();
        
        assertNotEquals(newUUIDEntity, otherUUIDEntity);
        assertNotEquals(newUUIDEntity.hashCode(), otherUUIDEntity.hashCode());
    }
    
    @Test
    public void hashCode_areTheSameWhenEqual() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        UUIDEntity sameUUIDEntity = new UUIDEntity();
        
        newUUIDEntity.setUUID(UUID_STRING);
        sameUUIDEntity.setUUID(UUID_STRING);
        
        assertEquals(newUUIDEntity, sameUUIDEntity);
        assertEquals(newUUIDEntity.hashCode(), sameUUIDEntity.hashCode());
    }
    
    @Test
    public void hashCode_isTheHashCodeOfId() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        assertEquals(newUUIDEntity.hashCode(), newUUIDEntity.getUuid().hashCode());
    }
    
    @Test
    public void toString_containsUUID() {
        UUIDEntity newUUIDEntity = new UUIDEntity();
        
        assertTrue(newUUIDEntity.toString().contains(newUUIDEntity.getUuid()));
    }
}
