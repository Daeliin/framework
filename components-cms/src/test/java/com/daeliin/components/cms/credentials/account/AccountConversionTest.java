package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.fixtures.AccountRows;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.sql.BAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountConversionTest {

    private AccountConversion accountConversion = new AccountConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        Account nullAccount = null;

        accountConversion.to(nullAccount);
    }

    @Test
    public void shouldMapAccount() {
        BAccount mappedAccount = accountConversion.to(AccountLibrary.admin());

        assertThat(mappedAccount).isEqualToComparingFieldByField(AccountRows.admin());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BAccount nullAccountRow = null;

        accountConversion.from(nullAccountRow);
    }

    @Test
    public void shouldInstantiateAnAccount() {
        Account rebuiltAccount = accountConversion.from(AccountRows.admin());

        assertThat(rebuiltAccount).isEqualTo(AccountLibrary.admin());
    }
}