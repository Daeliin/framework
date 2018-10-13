package com.blebail.components.cms.credentials.account;

import com.blebail.components.cms.sql.BAccount;
import com.blebail.components.cms.sql.QAccount;
import com.blebail.components.persistence.resource.repository.ResourceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class AccountRepository extends ResourceRepository<BAccount, String> {

    public AccountRepository() {
        super(QAccount.account, QAccount.account.id, BAccount::getId);
    }
}
