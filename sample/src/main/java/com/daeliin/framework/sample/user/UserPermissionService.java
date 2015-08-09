package com.daeliin.framework.sample.user;

import com.daeliin.framework.core.service.ResourceService;
import com.daeliin.framework.security.details.UserPermission;
import com.daeliin.framework.security.details.UserPermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionService extends ResourceService<UserPermission, Long, UserPermissionRepository> {
}
