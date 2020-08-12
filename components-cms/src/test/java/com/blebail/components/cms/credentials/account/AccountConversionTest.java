package com.blebail.components.cms.credentials.account;

import com.blebail.components.cms.fixtures.AccountRows;
import com.blebail.components.cms.library.AccountLibrary;
import com.blebail.components.cms.library.PersistenceConversionTest;
import com.blebail.components.cms.sql.BAccount;
import com.blebail.components.core.resource.Conversion;

public final class AccountConversionTest extends PersistenceConversionTest<Account, BAccount> {

    @Override
    protected Conversion<Account, BAccount> conversion() {
        return new AccountConversion();
    }

    @Override
    protected Account object() {
        return AccountLibrary.admin();
    }

    @Override
    protected BAccount converted() {
        return AccountRows.admin();
    }
}