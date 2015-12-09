package com.daeliin.framework.security.mock.permission.repository;

import com.daeliin.framework.security.mock.permission.model.Permission;
import com.daeliin.framework.commons.security.details.PermissionDetailsRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends PermissionDetailsRepository<Permission, Long> {
}
