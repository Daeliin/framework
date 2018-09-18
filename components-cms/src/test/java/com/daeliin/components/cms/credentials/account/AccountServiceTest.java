package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import com.daeliin.components.cms.fixtures.AccountRows;
import com.daeliin.components.cms.sql.BAccount;
import com.daeliin.components.cms.sql.QAccount;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public final class AccountServiceTest {

    private AccountRepository accountRepositoryMock;
    private PermissionService permissionServiceMock;
    private AccountService tested;

    @Before
    public void setUp() {
        accountRepositoryMock = mock(AccountRepository.class);
        permissionServiceMock = mock(PermissionService.class);

        tested = new AccountService(accountRepositoryMock, permissionServiceMock);
    }

    @Test
    public void shouldHitTheDatabase_whenLoadingAUserForTheFirstTime() {
        BAccount account = AccountRows.admin();;
        Permission permission = new Permission("admin", Instant.now(), "ADMIN");

        doReturn(List.of(account)).when(accountRepositoryMock).findAll(QAccount.account.username.equalsIgnoreCase(account.getUsername())
                .and(QAccount.account.enabled.isTrue()));

        doReturn(Set.of(permission)).when(permissionServiceMock).findForAccount(account.getId());

        UserDetails tomsearle = tested.loadUserByUsername(account.getUsername());

        assertThat(tomsearle).isNotNull();
        assertThat(tomsearle.getUsername()).isEqualTo(account.getUsername());
        assertThat(tomsearle.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_admin");

        verify(accountRepositoryMock).findAll(QAccount.account.username.equalsIgnoreCase(account.getUsername())
                .and(QAccount.account.enabled.isTrue()));
        verify(permissionServiceMock).findForAccount(account.getId());
    }

    @Test
    public void shouldNotHitTheDatabaseMoreThanOnce_whenUserAlreadyLoaded() {
        BAccount account = AccountRows.admin();;
        Permission permission = new Permission("admin", Instant.now(), "ADMIN");

        doReturn(List.of(account)).when(accountRepositoryMock).findAll(QAccount.account.username.equalsIgnoreCase(account.getUsername())
                .and(QAccount.account.enabled.isTrue()));

        doReturn(Set.of(permission)).when(permissionServiceMock).findForAccount(account.getId());

        tested.loadUserByUsername(account.getUsername());
        UserDetails tomsearle = tested.loadUserByUsername(account.getUsername());

        assertThat(tomsearle).isNotNull();
        assertThat(tomsearle.getUsername()).isEqualTo(account.getUsername());
        assertThat(tomsearle.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_admin");

        verify(accountRepositoryMock, times(1)).findAll(QAccount.account.username.equalsIgnoreCase(account.getUsername())
                .and(QAccount.account.enabled.isTrue()));
        verify(permissionServiceMock, times(1)).findForAccount(account.getId());
    }

}