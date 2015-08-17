package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.core.repository.ResourceRepository;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserDetailsRepository<E extends UserDetails, ID extends Serializable> extends ResourceRepository<E, ID> {
    
    public E findByUsernameIgnoreCaseAndEnabled(final String username, final boolean enabled);
}
