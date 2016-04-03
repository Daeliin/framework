package com.daeliin.framework.commons.security.credentials.permission;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class PersistentPermissionService extends ResourceService<PersistentPermission, Long, PersistentPermissionRepository> {
}
