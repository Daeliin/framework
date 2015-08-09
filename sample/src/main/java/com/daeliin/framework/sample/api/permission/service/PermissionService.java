package com.daeliin.framework.sample.api.permission.service;

import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.sample.api.permission.model.Permission;
import com.daeliin.framework.sample.api.permission.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ResourceService<Permission, Long, PermissionRepository> {
}
