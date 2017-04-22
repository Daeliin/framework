package com.daeliin.components.security.credentials.account;

import com.daeliin.components.security.fixtures.AccountFixtures;
import com.daeliin.components.security.library.AccountLibrary;
import com.daeliin.components.security.sql.BAccount;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountConversionTest {

    private AccountConversion accountConversion = new AccountConversion();

    @Test
    public void shouldMapToNull_whenNull() {
        assertThat(accountConversion.map(null)).isNull();
    }

    @Test
    public void shouldMapAccount() {
        BAccount mappedAccount = accountConversion.map(AccountLibrary.admin());

        assertThat(mappedAccount).isEqualToComparingFieldByField(AccountFixtures.admin());
    }

    @Test
    public void shouldInstantiateNull_fromNull() {
        assertThat(accountConversion.instantiate(null)).isNull();
    }

    @Test
    public void shouldInstantiateAnAccount() {
        Account rebuiltAccount = accountConversion.instantiate(AccountFixtures.admin());

        assertThat(rebuiltAccount).isEqualTo(AccountLibrary.admin());
    }
}