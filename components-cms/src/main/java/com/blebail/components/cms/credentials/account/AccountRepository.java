package com.blebail.components.cms.credentials.account;

import com.blebail.components.cms.sql.BAccount;
import com.blebail.components.cms.sql.QAccount;
import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Transactional
@Component
public class AccountRepository extends SpringCrudRepository<QAccount, BAccount, String> {

    @Inject
    public AccountRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QAccount.account, QAccount.account.id, BAccount::getId), queryFactory);
    }
}
