package com.daeliin.components.security.credentials.account;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import com.daeliin.components.security.sql.BAccount;
import com.daeliin.components.security.sql.QAccount;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class AccountRepository extends ResourceRepository<BAccount, String> {

    public AccountRepository() {
        super(QAccount.account, QAccount.account.id, BAccount::getId);
    }
}
