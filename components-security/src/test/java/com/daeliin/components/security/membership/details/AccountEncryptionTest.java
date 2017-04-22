package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.library.AccountLibrary;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountEncryptionTest {

    @Test(expected = Exception.class)
    public void shouldThrowException_whenUsernameIsNull() {
        new AccountEncryption(null, "password");
    }

    @Test(expected = Exception.class)
    public void shouldThrowException_whenPasswordIsNull() {
        new AccountEncryption("admin", null);
    }

    @Test
    public void shouldGenerateABCryptPassword() {
        Account account = AccountLibrary.admin();
        AccountEncryption userDetailsEncryption = new AccountEncryption(account.username, "password");

        assertThat(userDetailsEncryption.password.length()).isEqualTo(60);
        assertThat(userDetailsEncryption.password).startsWith("$2a$");
    }

    @Test
    public void shouldGenerateSha512TokenNotEqualToSha512OfUsername() {
        Account account = AccountLibrary.admin();
        AccountEncryption userDetailsEncryption = new AccountEncryption(account.username, "password");

        assertThat(userDetailsEncryption.token.length()).isEqualTo(128);
        assertThat(userDetailsEncryption.token).isNotEqualTo(DigestUtils.sha512Hex(account.username));
    }
}
