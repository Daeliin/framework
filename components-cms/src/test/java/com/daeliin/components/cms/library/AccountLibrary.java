package com.daeliin.components.cms.library;

import com.daeliin.components.cms.credentials.account.Account;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class AccountLibrary {

    public static Account admin() {
        return new Account(
                "ACCOUNT1",
                LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
                "admin",
                "admin@daeliin.com",
                true,
                "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6",
                "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e");
    }

    public static Account john() {
        return new Account(
            "ACCOUNT2",
            LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
            "john",
            "john@daeliin.com",
            true,
            "$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.s",
            "c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e");
    }

    public static Account inactive() {
        return new Account(
            "ACCOUNT3",
            LocalDateTime.of(2017, 1, 1, 12, 0, 0).toInstant(ZoneOffset.UTC),
            "inactive",
            "inactive@daeliin.com",
            false,
            "$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.",
            "c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e");
    }
}
