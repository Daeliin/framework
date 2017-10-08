package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.sql.BAccount;
import com.daeliin.components.cms.sql.QAccount;
import com.daeliin.components.persistence.resource.service.ResourceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class AccountService extends ResourceService<Account, BAccount, String, AccountRepository> {

    private final PermissionService permissionService;

    @Inject
    public AccountService(AccountRepository repository, PermissionService permissionService) {
        super(repository, new AccountConversion());
        this.permissionService = permissionService;
    }

    public Account findByUsernameAndEnabled(String username) {
        Collection<BAccount> bAccounts = repository.findAll(QAccount.account.username.equalsIgnoreCase(username)
                .and(QAccount.account.enabled.isTrue()));


        BAccount firstMatch = bAccounts.stream().findFirst().orElseThrow(NoSuchElementException::new);

        return conversion.instantiate(firstMatch);
    }

    public boolean usernameExists(String username) {
        return !repository.findAll(QAccount.account.username.equalsIgnoreCase(username)).isEmpty();
    }

    @Override
    public boolean delete(String id) {
        permissionService.deleteForAccount(id);
        return super.delete(id);
    }
}
