package com.daeliin.framework.security.details;

import com.daeliin.framework.core.repository.ResourceRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends ResourceRepository<UserPermission, Long> {
    
    public List<UserPermission> findByUser(final User user);
}
