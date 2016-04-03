package com.daeliin.framework.commons.security.credentials.permission;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentPermissionRepository extends ResourceRepository<PersistentPermission, Long> {
}
