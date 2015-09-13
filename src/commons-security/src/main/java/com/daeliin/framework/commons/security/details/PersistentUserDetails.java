package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@EqualsAndHashCode(of = {"username"}, callSuper = true)
@ToString(of = {"username", "enabled"}, callSuper = true)
@MappedSuperclass
public abstract class PersistentUserDetails extends LongIdPersistentResource implements UserDetails<Long>  {
    
    private static final long serialVersionUID = -1898848122717107177L;
    
    @Column(unique = true)
    @NotBlank
    private String username;
    
    @JsonIgnore
    @NotBlank
    private String password;
    
    @NotBlank
    private String token;
    
    @Column(name = "signedup_date")
    private Date signedUpDate;
    
    @NotNull
    private boolean enabled = false;
}
