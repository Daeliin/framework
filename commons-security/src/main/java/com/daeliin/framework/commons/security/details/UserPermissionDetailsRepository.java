package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserPermissionDetailsRepository<E extends UserPermissionDetails, ID extends Serializable> extends ResourceRepository<E, ID> {
    
    public List<E> findByUser(final UserDetails user);
}
