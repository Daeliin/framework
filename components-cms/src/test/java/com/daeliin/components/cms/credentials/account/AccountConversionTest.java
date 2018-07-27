package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.fixtures.AccountRows;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.library.PersistentConversionTest;
import com.daeliin.components.cms.sql.BAccount;
import com.daeliin.components.core.resource.Conversion;

public final class AccountConversionTest extends PersistentConversionTest<Account, BAccount> {

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