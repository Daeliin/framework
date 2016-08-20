package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends ResourceRepository<Permission, Long> {
}
