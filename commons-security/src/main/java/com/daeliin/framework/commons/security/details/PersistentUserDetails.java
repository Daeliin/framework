package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@EqualsAndHashCode(of = {"email", "username"}, callSuper = false)
@ToString(of = {"email", "username", "enabled"}, callSuper = true)
@MappedSuperclass
public abstract class PersistentUserDetails extends LongIdPersistentResource implements UserDetails<Long>  {
    
    private static final long serialVersionUID = -1898848122717107177L;
    
    @Email
    @Column(unique = true)
    @NotBlank
    private String email;
    
    @Column(unique = true)
    @NotBlank
    @Length(min = 3, max = 30)
    private String username;
    
    @Transient
    @Length(min = 8)
    private String clearPassword;
    
    @JsonIgnore
    private String password;
    
    private String token;
    
    @Column(name = "signedup_date")
    private Date signedUpDate;
    
    private boolean enabled = false;
}
