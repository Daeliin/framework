package com.daeliin.components.security.credentials.account;

import com.daeliin.components.domain.resource.Conversion;
import com.daeliin.components.security.sql.BAccount;

public final class AccountConversion implements Conversion<Account, BAccount> {

    public Account instantiate(BAccount bAccount) {
        if (bAccount == null) {
            return null;
        }

        return new Account(
            bAccount.getId(),
            bAccount.getCreationDate(),
            bAccount.getUsername(),
            bAccount.getEmail(),
            bAccount.getEnabled(),
            bAccount.getPassword(),
            bAccount.getToken());
    }

    @Override
    public BAccount map(Account account) {
        if (account == null) {
            return null;
        }

        return new BAccount(
            account.getCreationDate(),
            account.email,
            account.enabled,
            account.getId(),
            account.password,
            account.token,
            account.username);
    }
}
