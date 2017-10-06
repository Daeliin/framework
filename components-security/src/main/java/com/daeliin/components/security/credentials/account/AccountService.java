package com.daeliin.components.security.credentials.account;

import com.daeliin.components.persistence.resource.service.ResourceService;
import com.daeliin.components.security.credentials.permission.PermissionService;
import com.daeliin.components.security.sql.BAccount;
import com.daeliin.components.security.sql.QAccount;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

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


        Account account = conversion.instantiate(bAccounts.stream().findFirst().orElse(null));

        return account;
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
