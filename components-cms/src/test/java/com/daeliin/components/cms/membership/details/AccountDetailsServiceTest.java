package com.daeliin.components.cms.membership.details;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.credentials.account.AccountService;
import com.daeliin.components.cms.credentials.permission.Permission;
import com.daeliin.components.cms.credentials.permission.PermissionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public final class AccountDetailsServiceTest {

    private AccountService accountServiceMock;
    private PermissionService permissionServiceMock;
    private AccountDetailsService tested;

    @Before
    public void setUp() {
        accountServiceMock = mock(AccountService.class);
        permissionServiceMock = mock(PermissionService.class);

        tested = new AccountDetailsService(accountServiceMock, permissionServiceMock);
    }

    @Test
    public void shouldNotHitTheDatabase_whenLoadingAUserForTheFirstTime() {
        String username = "tomsearle";
        Account account = new Account("tomsearle", Instant.now(), "tomsearle", "tomsearle@architects.com", true, "pass", "token");
        Permission permission = new Permission("admin", Instant.now(), "ADMIN");

        doReturn(account).when(accountServiceMock).findByUsernameAndEnabled(username);
        doReturn(Set.of(permission)).when(permissionServiceMock).findForAccount(account.getId());

        UserDetails tomsearle = tested.loadUserByUsername(username);

        assertThat(tomsearle).isNotNull();
        assertThat(tomsearle.getUsername()).isEqualTo(username);
        assertThat(tomsearle.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_admin");

        verify(accountServiceMock).findByUsernameAndEnabled(username);
        verify(permissionServiceMock).findForAccount(account.getId());
    }

    @Test
    public void shouldNotHitTheDatabaseMoreThanOne_whenUserAlreadyLoaded() {
        String username = "tomsearle";
        Account account = new Account("tomsearle", Instant.now(), "tomsearle", "tomsearle@architects.com", true, "pass", "token");
        Permission permission = new Permission("admin", Instant.now(), "ADMIN");

        doReturn(account).when(accountServiceMock).findByUsernameAndEnabled(username);
        doReturn(Set.of(permission)).when(permissionServiceMock).findForAccount(account.getId());

        tested.loadUserByUsername(username);
        UserDetails tomsearle = tested.loadUserByUsername(username);

        assertThat(tomsearle).isNotNull();
        assertThat(tomsearle.getUsername()).isEqualTo(username);
        assertThat(tomsearle.getAuthorities()).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_admin");

        verify(accountServiceMock, times(1)).findByUsernameAndEnabled(username);
        verify(permissionServiceMock, times(1)).findForAccount(account.getId());
    }
}