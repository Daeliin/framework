package com.daeliin.framework.security.mock.userpermission.service;

import com.daeliin.framework.core.resource.service.ResourceService;
import com.daeliin.framework.security.mock.userpermission.model.UserPermission;
import com.daeliin.framework.security.mock.userpermission.repository.UserPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionService extends ResourceService<UserPermission, Long, UserPermissionRepository> {
}
