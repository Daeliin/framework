package com.daeliin.components.security.credentials.account;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"email", "username", "enabled"}, callSuper = true)
@Entity
public class Account extends UUIDPersistentResource implements Comparable<Account> {
    
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

    public Account(String email, String username, String clearPassword, String password, String token, LocalDateTime signUpDate) {
        this.email = email;
        this.username = username;
        this.clearPassword = clearPassword;
        this.password = password;
        this.token = token;
        this.signUpDate = signUpDate;
    }
    
    @Override
    public int compareTo(Account other) {
        boolean usernamesAreNotBlanks = StringUtils.isNotBlank(this.username) && StringUtils.isNotBlank(other.username);
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (usernamesAreNotBlanks) {
            return this.username.compareTo(other.username);
        } else {
            return -1;
        }
    }
}
