package com.daeliin.framework.security.credentials.permission;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends ResourceRepository<Permission, Long> {
}
