package com.daeliin.components.security.credentials.account;

import com.daeliin.components.core.resource.repository.ResourceRepository;
import com.daeliin.components.security.sql.BAccount;
import com.daeliin.components.security.sql.BPermission;
import com.daeliin.components.security.sql.QAccount;
import com.daeliin.components.security.sql.QAccountPermission;
import com.daeliin.components.security.sql.QPermission;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Component
public class AccountRepository extends ResourceRepository<BAccount, String> {

    public AccountRepository() {
        super(QAccount.account, QAccount.account.id, BAccount::getId);
    }

    public Collection<BPermission> findPermissions(String accountId) {
        if (Strings.isNullOrEmpty(accountId)) {
            return new ArrayList<>();
        }

        return queryFactory.select(QPermission.permission)
                .from(QAccountPermission.accountPermission, QPermission.permission)
                .where(QAccountPermission.accountPermission.permissionId.eq(QPermission.permission.id)
                        .and(QAccountPermission.accountPermission.accountId.eq(accountId)))
                .fetch();
    }
}
