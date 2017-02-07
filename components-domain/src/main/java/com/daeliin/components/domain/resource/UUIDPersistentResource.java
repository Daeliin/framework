package com.daeliin.components.domain.resource;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Resource saved in a RDBMS and identified by an UUID, 
 * equality is only based on this UUID.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class UUIDPersistentResource implements PersistentResource<Long>, UUIDResource {
    
    private static final long serialVersionUID = -5886577401324234159L;
    
    @Id
    @GeneratedValue
    protected Long id;
    
    @NotBlank
    @Column(unique = true)
    protected String uuid = java.util.UUID.randomUUID().toString();
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other == null || !(other instanceof UUIDPersistentResource)) {
            return false;
        }

        UUIDPersistentResource otherUUIDPersistentResource = (UUIDPersistentResource)other;

        return uuid.equals(otherUUIDPersistentResource.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        
        toStringBuilder
            .append(" [uuid=")
            .append(uuid)
            .append("]");
        
        return toStringBuilder.toString();
    }
}
