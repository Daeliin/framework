package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.sql.BPermission;
import com.daeliin.components.security.sql.QPermission;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads permission in memory, and caches it.
 */
@Transactional
@Component
public class PermissionRepository extends ResourceRepository<BPermission, String> {

    public PermissionRepository() {
        super(QPermission.permission, QPermission.permission.id, BPermission::getId);
    }
}
