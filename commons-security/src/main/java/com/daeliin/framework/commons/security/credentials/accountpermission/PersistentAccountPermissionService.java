package com.daeliin.framework.commons.security.credentials.accountpermission;

import com.daeliin.framework.core.resource.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class PersistentAccountPermissionService extends ResourceService<PersistentAccountPermission, Long, PersistentAccountPermissionRepository> {
}
