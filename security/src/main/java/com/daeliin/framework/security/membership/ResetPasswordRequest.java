package com.daeliin.framework.security.membership;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotNull
    private Long accountId;
    
    @NotBlank
    private String token;
    
    @NotBlank
    private String newPassword;
}
