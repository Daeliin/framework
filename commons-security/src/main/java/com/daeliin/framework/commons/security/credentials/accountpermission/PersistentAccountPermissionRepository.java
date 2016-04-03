package com.daeliin.framework.commons.security.credentials.accountpermission;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.core.resource.repository.ResourceRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentAccountPermissionRepository extends ResourceRepository<PersistentAccountPermission, Long> {
    
    public List<AccountPermission> findByAccount(final Account account);
}
