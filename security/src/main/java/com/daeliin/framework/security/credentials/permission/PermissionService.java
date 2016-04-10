package com.daeliin.framework.security.credentials.permission;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ResourceService<Permission, Long, PermissionRepository> {
}
