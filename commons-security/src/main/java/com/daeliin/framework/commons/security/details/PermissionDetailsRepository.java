package com.daeliin.framework.commons.security.details;

import com.daeliin.framework.core.repository.ResourceRepository;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PermissionDetailsRepository <E extends PermissionDetails, ID extends Serializable> extends ResourceRepository<E, ID> {
}
