package com.daeliin.framework.commons.security.credentials.accountpermission;

import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.core.resource.repository.ResourceRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPermissionRepository extends ResourceRepository<AccountPermission, Long> {
    
    public List<AccountPermission> findByAccount(final Account account);
}
