package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.security.sql.BPermission;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public final class PermissionService extends ResourceService<Permission, BPermission, String, PermissionRepository> {

    @Inject
    public PermissionService(PermissionRepository repository) {
        super(repository, new PermissionConversion());
    }
}
