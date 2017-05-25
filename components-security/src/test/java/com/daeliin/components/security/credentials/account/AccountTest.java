package com.daeliin.components.security.credentials.account;

import com.daeliin.components.domain.resource.PersistentResource;
import com.daeliin.components.security.library.AccountLibrary;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public final class AccountTest {

    @Test
    public void shouldExtendPersistentResource() {
        assertThat(Account.class.getClass().getSuperclass().getClass()).isEqualTo(PersistentResource.class.getClass());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExeption_whenUsernameIsNull() {
        new Account("ACCOUNT4", LocalDateTime.now(), null, "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }

    @Test
    public void shouldAssignAnUsername() {
        Account account = new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");

        assertThat(account.username).isEqualTo("user");
    }

    @Test(expected = Exception.class)
    public void shouldThrowExeption_whenEmailIsNull() {
        new Account("ACCOUNT4", LocalDateTime.now(), "user", null, true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }

    @Test
    public void shouldAssignAnEmail() {
        Account account = new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");

        assertThat(account.email).isEqualTo("user@email.org");
    }

    @Test(expected = Exception.class)
    public void shouldThrowExeption_whenPasswordIsNull() {
        new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                null,
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }

    @Test
    public void shouldAssignAPassword() {
        Account account = new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");

        assertThat(account.password).isEqualTo("$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6");
    }

    @Test(expected = Exception.class)
    public void shouldThrowExeption_whenTokenIsNull() {
        new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                null);
    }

    @Test
    public void shouldAssignAToken() {
        Account account = new Account("ACCOUNT4", LocalDateTime.now(), "user", "user@email.org", true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");

        assertThat(account.token).isEqualTo("b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }

    @Test
    public void shouldPrintItsIdEmailUsernameAndEnabled() {
        Account account = AccountLibrary.admin();

        assertThat(account.toString()).contains(
                account.getId(),
                account.email,
                account.username,
                String.valueOf(account.enabled));
    }

    @Test
    public void shouldBeComparedOnUsernames() {
        Account account1 = AccountLibrary.admin();
        Account account2 = AccountLibrary.john();

        assertThat(account1.compareTo(account2)).isNegative();
        assertThat(account2.compareTo(account1)).isPositive();
    }
}