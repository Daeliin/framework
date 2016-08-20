package com.daeliin.components.security.credentials.accountpermission;

import com.daeliin.components.security.credentials.accountpermission.AccountPermissionRepository;
import com.daeliin.components.security.credentials.accountpermission.AccountPermission;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.account.AccountRepository;
import com.daeliin.components.security.credentials.permission.Permission;
import com.daeliin.components.security.credentials.permission.PermissionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class)
public class AccountPermissionRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
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
