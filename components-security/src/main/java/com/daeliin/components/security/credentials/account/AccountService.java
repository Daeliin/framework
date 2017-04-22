package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.service.ResourceService;
import com.daeliin.components.security.credentials.permission.Permission;
import com.daeliin.components.security.credentials.permission.PermissionConversion;
import com.daeliin.components.security.sql.BAccount;
import com.daeliin.components.security.sql.QAccount;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Service
public class AccountService extends ResourceService<Account, BAccount, String, AccountRepository> {

    protected final PermissionConversion permissionConversion;

    @Inject
    public AccountService(AccountRepository repository) {
        super(repository, new AccountConversion());
        permissionConversion = new PermissionConversion();
    }

    public Account findByUsernameAndEnabled(String username) {
        Collection<BAccount> bAccounts = repository.findAll(QAccount.account.username.equalsIgnoreCase(username)
                .and(QAccount.account.enabled.isTrue()));


        Account account = instantiate(bAccounts.stream().findFirst().orElse(null));

        return account;
    }

    public Collection<Permission> findPermissions(Account account) {
        return repository.findPermissions(account.id())
                .stream()
                .map(permissionConversion::instantiate)
                .collect(toSet());
    }

    public boolean usernameExists(String username) {
        return !repository.findAll(QAccount.account.username.equalsIgnoreCase(username)).isEmpty();
    }
}
