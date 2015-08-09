package com.daeliin.framework.security.details;

import com.daeliin.framework.core.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends ResourceRepository<Permission, Long> {
}
