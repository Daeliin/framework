package com.daeliin.framework.commons.security.credentials.permission;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(of = {"label"}, callSuper = true)
@ToString(of = {"label"}, callSuper = true)
@Table(name = "permission")
@Entity
public class Permission extends LongIdPersistentResource {
    
    private static final long serialVersionUID = -4768460974607663983L;
    
    @Column(unique = true)
    @NotBlank
    private String label;
}
