package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.fixtures.PermissionFixtures;
import com.daeliin.components.security.library.AccountLibrary;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class AccountRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountRepository accountRepository;

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(accountRepository.getClass().getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }

    @Test
    public void shouldReturnEmptyCollection_whenFindingAccountPermissionOfNull() {
        assertThat(accountRepository.findPermissions(null)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollection_whenAccountHasNoPermission() {
        assertThat(accountRepository.findPermissions(AccountLibrary.inactive().getId())).isEmpty();
    }

    @Test
    public void shouldFindPermissionsOfAccount() {
        assertThat(accountRepository.findPermissions(AccountLibrary.admin().getId()))
                .usingFieldByFieldElementComparator()
                .containsOnly(PermissionFixtures.admin());
    }
}
