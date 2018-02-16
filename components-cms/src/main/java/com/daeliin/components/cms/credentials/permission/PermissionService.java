package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.sql.BPermission;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toCollection;

@Service
public final class PermissionService extends ResourceService<Permission, BPermission, String, PermissionRepository> {

    @Inject
    public PermissionService(PermissionRepository repository) {
        super(repository, new PermissionConversion());
    }

    public Set<Permission> findForAccount(String accountId) {
        return repository.findForAccount(accountId)
                .stream()
                .map(conversion::from)
                .collect(toCollection(TreeSet::new));
    }

    public void addToAccount(String accountId, String permissionId) {
        repository.addToAccount(accountId, permissionId);
    }

    public void deleteForAccount(String accountId) {
        repository.deleteForAccount(accountId);
    }

    public void deleteForAccount(String accountId, String permissionId) {
        repository.deleteForAccount(accountId, permissionId);
    }

    public boolean isUsed(String permissionId) {
        return repository.isUsed(permissionId);
    }
}
