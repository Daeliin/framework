package com.daeliin.components.cms.library;

import com.daeliin.components.security.credentials.account.Account;

import java.time.LocalDateTime;

public final class AccountLibrary {

    public static Account admin() {
        return new Account(
                "ACCOUNT1",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                "admin",
                "admin@daeliin.com",
                true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }
}
