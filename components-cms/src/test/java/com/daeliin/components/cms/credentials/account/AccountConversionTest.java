package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.fixtures.AccountFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.sql.BAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountConversionTest {

    private AccountConversion accountConversion = new AccountConversion();

    @Test(expected = Exception.class)
    public void shouldThrowException_whenMappingNull() {
        Account nullAccount = null;

        accountConversion.map(nullAccount);
    }

    @Test
    public void shouldMapAccount() {
        BAccount mappedAccount = accountConversion.map(AccountLibrary.admin());

        assertThat(mappedAccount).isEqualToComparingFieldByField(AccountFixtures.admin());
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenInstantiatingNull() {
        BAccount nullAccountRow = null;

        accountConversion.instantiate(nullAccountRow);
    }

    @Test
    public void shouldInstantiateAnAccount() {
        Account rebuiltAccount = accountConversion.instantiate(AccountFixtures.admin());

        assertThat(rebuiltAccount).isEqualTo(AccountLibrary.admin());
    }
}