package com.daeliin.framework.commons.security.credentials.accountpermission;

import com.daeliin.framework.commons.model.PersistentResource;
import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.credentials.permission.Permission;

public interface AccountPermission extends PersistentResource<Long> {
    
    Account getAccount();
    
    Permission getPermission();
}
