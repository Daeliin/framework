package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.sql.BAccount;
import com.daeliin.components.cms.sql.QAccount;
import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class AccountRepository extends ResourceRepository<BAccount, String> {

    public AccountRepository() {
        super(QAccount.account, QAccount.account.id, BAccount::getId);
    }
}
