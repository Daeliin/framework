package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ResourceService<Permission, Long, PermissionRepository> {
}
