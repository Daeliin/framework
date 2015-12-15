package com.daeliin.framework.security.mock.permission.service;

import com.daeliin.framework.core.resource.service.ResourceService;
import com.daeliin.framework.security.mock.permission.model.Permission;
import com.daeliin.framework.security.mock.permission.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ResourceService<Permission, Long, PermissionRepository> {
}
