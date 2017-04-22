package com.daeliin.components.cms.fixtures;

import com.daeliin.components.security.sql.BAccount;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class AccountFixtures {

    public static BAccount admin() {
        return new BAccount(
                Timestamp.valueOf(LocalDateTime.of(2017, 1, 1, 12, 0, 0)),
                "admin@daeliin.com",
                true,
                "ACCOUNT1",
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e",
                "admin");
    }
}
