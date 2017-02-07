package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"label"}, callSuper = true)
@Entity
public class Permission extends UUIDPersistentResource implements Comparable<Permission> {
    
    private static final long serialVersionUID = -4768460974607663983L;
    
    @Column(unique = true)
    @NotBlank
    private String label;
    
    @Override
    public int compareTo(Permission other) {
        boolean labelsAreNotBlanks = this.label != null && other.label != null;
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (labelsAreNotBlanks) {
            return this.label.compareTo(other.label);
        } else {
            return -1;
        }
    }
}
