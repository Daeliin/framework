package com.daeliin.components.security.credentials.account;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@ToString(of = {"email", "username", "enabled"}, callSuper = true)
@Entity
public class Account extends UUIDPersistentResource {
    
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
    
    @JsonIgnore
    private String token;
    
    @Column(name = "signup_date")
    private LocalDateTime signUpDate;
    
    private boolean enabled = false;
}
