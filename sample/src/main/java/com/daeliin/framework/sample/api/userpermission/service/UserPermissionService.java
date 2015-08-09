package com.daeliin.framework.sample.api.userpermission.service;

import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.sample.api.userpermission.model.UserPermission;
import com.daeliin.framework.sample.api.userpermission.repository.UserPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionService extends ResourceService<UserPermission, Long, UserPermissionRepository> {
}
