package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString(of = {"label"}, callSuper = true)
@Entity
public class Permission extends UUIDPersistentResource {
    
    private static final long serialVersionUID = -4768460974607663983L;
    
    @Column(unique = true)
    @NotBlank
    private String label;
}
