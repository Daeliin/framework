package com.blebail.components.cms.membership.details;

import com.blebail.components.cms.credentials.account.Account;
import com.blebail.components.cms.library.AccountLibrary;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountEncryptionTest {

    @Test
    public void shouldThrowException_whenUsernameIsNull() {
        assertThrows(Exception.class, () -> new AccountEncryption(null, "password"));
    }

    @Test
    public void shouldThrowException_whenPasswordIsNull() {
        assertThrows(Exception.class, () -> new AccountEncryption("admin", null));
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
