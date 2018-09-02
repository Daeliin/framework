package com.daeliin.components.cms.credentials.permission;

import com.daeliin.components.cms.credentials.account.Account;
import com.daeliin.components.cms.fixtures.JavaFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.PermissionLibrary;
import com.daeliin.components.test.rule.DbFixture;
import com.daeliin.components.test.rule.DbMemory;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PermissionServiceIT {

    @Inject
    private PermissionService permissionService;

    @ClassRule
    public static DbMemory dbMemory = new DbMemory();

    @Rule
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

        Collection<Permission> permissions = permissionService.findForAccount(account.getId());

        assertThat(permissions).containsExactly(PermissionLibrary.admin());

        dbFixture.noRollback();
    }
}
