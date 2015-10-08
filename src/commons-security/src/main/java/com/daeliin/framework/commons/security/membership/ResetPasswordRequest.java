package com.daeliin.framework.commons.security.membership;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class ResetPasswordRequest<ID extends Serializable> {

    @NotNull
    private ID userDetailsId;
    
    @NotBlank
    private String token;
    
    @NotBlank
    private String newPassword;
}
