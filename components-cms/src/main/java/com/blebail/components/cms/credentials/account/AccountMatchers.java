package com.blebail.components.cms.credentials.account;

import com.blebail.components.cms.sql.QAccount;
import com.querydsl.core.types.dsl.BooleanExpression;

public final class AccountMatchers {

    public static BooleanExpression usernameEqualsCaseInsensitive(String username) {
        return QAccount.account.username.equalsIgnoreCase(username);
    }
}
