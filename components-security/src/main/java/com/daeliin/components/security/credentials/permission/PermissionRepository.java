package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.PagingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends PagingRepository<Permission, Long> {
}
