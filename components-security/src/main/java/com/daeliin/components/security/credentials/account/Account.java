package com.daeliin.components.security.credentials.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@Getter
@Setter
public class Account implements Comparable<Account> {

    private static final long serialVersionUID = -1898848122717107177L;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 3, max = 30)
    private String username;

    @Length(min = 8)
    private String clearPassword;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String token;

    private LocalDateTime signUpDate;

    private boolean enabled = false;

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
