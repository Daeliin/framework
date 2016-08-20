package com.daeliin.components.security.credentials.accountpermission;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.permission.Permission;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPermissionRepository extends ResourceRepository<AccountPermission, Long> {
    
    public List<AccountPermission> findByAccount(Account account);
    
    public List<AccountPermission> findByPermission(Permission permission);
}
