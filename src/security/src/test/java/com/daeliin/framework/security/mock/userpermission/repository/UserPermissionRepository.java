package com.daeliin.framework.security.mock.userpermission.repository;

import com.daeliin.framework.commons.security.details.UserPermissionDetailsRepository;
import com.daeliin.framework.security.mock.userpermission.model.UserPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends UserPermissionDetailsRepository<UserPermission, Long> {
}
