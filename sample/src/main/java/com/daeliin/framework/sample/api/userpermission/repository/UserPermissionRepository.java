package com.daeliin.framework.sample.api.userpermission.repository;

import com.daeliin.framework.sample.api.userpermission.model.UserPermission;
import com.daeliin.framework.commons.security.details.UserPermissionDetailsRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends UserPermissionDetailsRepository<UserPermission, Long> {
}
