package com.daeliin.components.cms.fixtures;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;

public final class JavaFixtures {

    public static Operation account() {
        return sequenceOf(
            Operations.deleteAllFrom("account"),
            Operations.insertInto("account")
            .columns("id", "username", "email", "enabled", "password", "token", "creation_date")
            .values("ACCOUNT1", "admin", "admin@daeliin.com", true, "$2a$10$ggIHKT/gYkYk0Bt2yP4xvOybahPn7GfSwC0T3fYhCzrZ9ta9LAYt6", "b5e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802af8e97fbed4999c0dd838577590d9e", "2017-01-01 12:00:00")
            .values("ACCOUNT2", "john", "john@daeliin.com", true, "$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.", "c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e", "2017-01-01 12:00:00")
            .values("ACCOUNT3", "inactive", "inactive@daeliin.com", false, "$2a$10$dJ9dhL7FqCDXBb1kKj5y.ub4ohRm7VARPLlg5H.AHcd/T/XVEgpA.", "c4e655641f1d05a415d5ea30d4fd25dcd03ea4a187c5d121d221454c03770f9f98101c206878b25697a79c924149da6802ad8997fbed4999c0dd838577590d9e", "2017-01-01 12:00:00")
            .build()
        );
    }

    public static Operation permission() {
        return sequenceOf(
            Operations.deleteAllFrom("permission"),
            Operations.insertInto("permission")
            .columns("id", "name", "creation_date")
            .values("ADMIN", "ADMIN", "2017-05-05 12:00:00")
            .values("USER", "USER", "2017-05-05 12:00:00")
            .values("PUBLISHER", "PUBLISHER", "2017-05-05 12:00:00")
            .build()
        );
    }

    public static Operation account_permission() {
        return sequenceOf(
            Operations.deleteAllFrom("account_permission"),
            Operations.insertInto("account_permission")
            .columns("account_id", "permission_id")
            .values("ACCOUNT1", "ADMIN")
            .values("ACCOUNT2", "USER")
            .build()
        );
    }

    public static Operation news() {
        return sequenceOf(
            Operations.deleteAllFrom("news"),
            Operations.insertInto("news")
            .columns("id", "creation_date", "author_id", "title", "url_friendly_title", "description", "content", "source", "publication_date", "status")
            .values("NEWS1", "2016-05-20 14:30:00", "ACCOUNT1", "Welcome to sample.com", "welcome-to-sample", "Today is the day we start sample.com", "We open our door today, you'll find content very soon.", "https://google.com", "2016-05-20 15:30:00", "PUBLISHED")
            .values("NEWS2", "2016-05-21 14:30:00", "ACCOUNT2", "Sample.com is live", "sample-is-live", "Today is the day we go live at sample.com", "We go live today, here's our first content.", null, null, "VALIDATED")
            .values("NEWS3", "2016-05-21 14:30:00", "ACCOUNT2", "Sample.com is in beta", "sample-is-in-beta", "Today is the day we go beta at sample.com", "We go beta today.", null, null, "DRAFT")
            .build()
        );
    }

    public static Operation country() {
        return sequenceOf(
            Operations.deleteAllFrom("country"),
            Operations.insertInto("country")
            .columns("code", "name", "creation_date")
            .values("FR", "France", "2017-01-01 12:00:00")
            .values("BE", "Belgium", "2017-01-01 12:00:00")
            .build()
        );
    }

    public static Operation event_log() {
        return sequenceOf(
            Operations.deleteAllFrom("event_log"),
            Operations.insertInto("event_log")
            .columns("id", "creation_date", "description")
            .values("d5666c5a-df28-49be-b711-cfd58d0867cf", "2017-01-01 12:00:00", "membership.loginmembership.login")
            .build()
        );
    }
}
