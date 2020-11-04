package com.blebail.components.cms.credentials.account;

import com.blebail.components.cms.credentials.permission.Permission;
import com.blebail.components.cms.credentials.permission.PermissionService;
import com.blebail.components.cms.sql.BAccount;
import com.blebail.components.cms.sql.QAccount;
import com.blebail.components.persistence.resource.service.ResourceService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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

    private Cache<String, Pair<Account, Collection<GrantedAuthority>>> accountByUsername = Caffeine.newBuilder()
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
        Pair<Account, Collection<GrantedAuthority>> account = accountByUsername.get(username, ignored -> {
            Account accountFromDatabase = findByUsernameAndEnabled(username);

            if (accountFromDatabase == null) {
                throw new UsernameNotFoundException("Username not found");
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            Collection<Permission> permissions = permissionService.findForAccount(accountFromDatabase.id());
            permissions.forEach(accountPermission -> authorities.add(new SimpleGrantedAuthority("ROLE_" + accountPermission.id())));

            return new ImmutablePair<>(accountFromDatabase, authorities);
        });

        return new User(account.getLeft().username, account.getLeft().password, account.getRight());
    }

    public void invalidateCache() {
        this.accountByUsername.invalidateAll();
    }

    public Account findByUsername(String username) {
        BAccount bAccount = repository.find(AccountMatchers.usernameEqualsCaseInsensitive(username))
                .stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        return conversion.from(bAccount);
    }

    public Account findByUsernameAndEnabled(String username) {
        Collection<BAccount> bAccounts = repository.find(AccountMatchers.usernameEqualsCaseInsensitive(username)
                .and(QAccount.account.enabled.isTrue()));


        BAccount firstMatch = bAccounts.stream().findFirst().orElseThrow(NoSuchElementException::new);

        return conversion.from(firstMatch);
    }

    public boolean usernameExists(String username) {
        return !repository.find(AccountMatchers.usernameEqualsCaseInsensitive(username)).isEmpty();
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
