package com.daeliin.components.security.credentials.accountpermission;

import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountRepository;
import com.daeliin.components.security.credentials.permission.Permission;
import com.daeliin.components.security.credentials.permission.PermissionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(classes = Application.class)
public class AccountPermissionRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Inject
    private AccountRepository accountRepository;
    
    @Inject
    private PermissionRepository permissionRepository;
    
    @Inject
    private AccountPermissionRepository accountPermissionRepository;
    
    @Test
    public void findByAccount_null_returnsEmptyList() {
        assertNotNull(accountPermissionRepository.findByAccount(null));
        assertEquals(accountPermissionRepository.findByAccount(null).size(), 0);
    }
    
    @Test
    public void findByAccount_account_returnsAccountPermissionsCorrespondingToTheAccount() {
        Account account = accountRepository.findOne(1L);
        List<AccountPermission> actualAccountPermissions = accountPermissionRepository.findByAccount(account);
        List<AccountPermission> expectedAccountPermissions = new ArrayList<>(Arrays.asList(accountPermissionRepository.findOne(1L)));
        
        assertNotNull(actualAccountPermissions);
        assertEquals(actualAccountPermissions, expectedAccountPermissions);
    }
    
    @Test
    public void findByPermission_null_returnsEmptyList() {
        assertNotNull(accountPermissionRepository.findByPermission(null));
        assertEquals(accountPermissionRepository.findByPermission(null).size(), 0);
    }
    
    @Test
    public void findByPermission_permission_returnsAccountPermissionsCorrespondingToThePermission() {
        Permission permission = permissionRepository.findOne(1L);
        List<AccountPermission> actualAccountPermissions = accountPermissionRepository.findByPermission(permission);
        List<AccountPermission> expectedAccountPermissions = new ArrayList<>(Arrays.asList(accountPermissionRepository.findOne(1L)));
        
        assertNotNull(actualAccountPermissions);
        assertEquals(actualAccountPermissions, expectedAccountPermissions);
    }
}
