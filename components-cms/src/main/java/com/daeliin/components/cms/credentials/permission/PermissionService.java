package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.sql.BPermission;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

@Service
public class PermissionService extends ResourceService<Permission, BPermission, String, PermissionRepository> {

    private final AccountService accountService;

    @Inject
    public PermissionService(PermissionRepository repository, AccountService accountService) {
        super(repository, new PermissionConversion());
        this.accountService = requireNonNull(accountService);
    }

    public Set<Permission> findForAccount(String accountId) {
        return repository.findForAccount(accountId)
                .stream()
                .map(conversion::from)
                .collect(toCollection(TreeSet::new));
    }

    public void addToAccount(String accountId, String permissionId) {
        repository.addToAccount(accountId, permissionId);
        accountService.invalidateCache();
    }

    public void deleteForAccount(String accountId) {
        repository.deleteForAccount(accountId);
        accountService.invalidateCache();
    }

    public void deleteForAccount(String accountId, String permissionId) {
        repository.deleteForAccount(accountId, permissionId);
        accountService.invalidateCache();
    }

    public boolean isUsed(String permissionId) {
        return repository.isUsed(permissionId);
    }
}
