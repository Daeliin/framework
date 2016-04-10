package com.daeliin.framework.security.credentials.accountpermissoin;

import com.daeliin.framework.core.resource.repository.ResourceRepository;
import com.daeliin.framework.security.credentials.account.Account;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPermissionRepository extends ResourceRepository<AccountPermission, Long> {
    
    public List<AccountPermission> findByAccount(final Account account);
}
