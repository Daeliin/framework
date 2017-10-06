package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.cms.fixtures.AccountFixtures;
import com.daeliin.components.cms.library.AccountLibrary;
import com.daeliin.components.cms.sql.BAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountConversionTest {

    private AccountConversion accountConversion = new AccountConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        Account nullAccount = null;

        assertThat(accountConversion.map(nullAccount)).isNull();
    }

    @Test
    public void shouldMapAccount() {
        BAccount mappedAccount = accountConversion.map(AccountLibrary.admin());

        assertThat(mappedAccount).isEqualToComparingFieldByField(AccountFixtures.admin());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        BAccount nullAccountRow = null;

        assertThat(accountConversion.instantiate(nullAccountRow)).isNull();
    }

    @Test
    public void shouldInstantiateAnAccount() {
        Account rebuiltAccount = accountConversion.instantiate(AccountFixtures.admin());

        assertThat(rebuiltAccount).isEqualTo(AccountLibrary.admin());
    }
}