package com.blebail.components.cms.credentials.permission;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.cms.fixtures.JavaFixtures;
import com.blebail.components.cms.library.AccountLibrary;
import com.blebail.components.cms.library.PermissionLibrary;
import com.blebail.components.test.rule.DbFixture;
import com.blebail.components.test.rule.DbMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PermissionServiceIT {

    @Inject
    private PermissionService permissionService;

    @RegisterExtension
    public static DbMemory dbMemory = new DbMemory();

    @RegisterExtension
    public DbFixture dbFixture = new DbFixture(dbMemory,
        sequenceOf(
            JavaFixtures.account(),
            JavaFixtures.permission(),
            JavaFixtures.account_permission()
        )
    );

    @Test
    public void shouldFindPermissionsOfAccount() {
        Account account = AccountLibrary.admin();

        Collection<Permission> permissions = permissionService.findForAccount(account.id());

        assertThat(permissions).containsExactly(PermissionLibrary.admin());

        dbFixture.readOnly();
    }
}
