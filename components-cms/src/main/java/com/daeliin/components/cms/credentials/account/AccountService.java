package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.sql.BAccount;
import com.daeliin.components.cms.sql.QAccount;
import com.daeliin.components.persistence.resource.service.ResourceService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService extends ResourceService<Account, BAccount, String, AccountRepository> implements UserDetailsService {

    private final PermissionService permissionService;

    private Cache<String, UserDetails> userDetailsByUsername = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.DAYS)
            .maximumSize(10_000)
            .build();

    @Inject
    public AccountService(AccountRepository repository, @Lazy PermissionService permissionService) {
        super(repository, new AccountConversion());
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDetailsByUsername.get(username, ignored -> {
            Account account = findByUsernameAndEnabled(username);

            if (account == null) {
                throw new UsernameNotFoundException("Username not found");
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            Collection<Permission> permissions = permissionService.findForAccount(account.getId());
            permissions.forEach(accountPermission -> authorities.add(new SimpleGrantedAuthority("ROLE_" + accountPermission.getId())));

            return new User(account.username, account.password, authorities);
        });
    }

    public void invalidateCache() {
        this.userDetailsByUsername.invalidateAll();
    }

    public Account findByUsername(String username) {
        BAccount bAccount = repository.findAll(QAccount.account.username.equalsIgnoreCase(username))
                .stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        return conversion.from(bAccount);
    }

    public Account findByUsernameAndEnabled(String username) {
        Collection<BAccount> bAccounts = repository.findAll(QAccount.account.username.equalsIgnoreCase(username)
                .and(QAccount.account.enabled.isTrue()));


        BAccount firstMatch = bAccounts.stream().findFirst().orElseThrow(NoSuchElementException::new);

        return conversion.from(firstMatch);
    }

    public boolean usernameExists(String username) {
        return !repository.findAll(QAccount.account.username.equalsIgnoreCase(username)).isEmpty();
    }

    @Override
    public Account update(Account resource) {
        Account updatedAccount = super.update(resource);

        invalidateCache();

        return updatedAccount;
    }

    @Override
    public Collection<Account> update(Collection<Account> resources) {
        Collection<Account> updatedAccounts = super.update(resources);

        invalidateCache();

        return updatedAccounts;
    }

    @Override
    public boolean delete(String id) {
        permissionService.deleteForAccount(id);

        boolean deleted = super.delete(id);

        invalidateCache();

        return deleted;
    }
}
