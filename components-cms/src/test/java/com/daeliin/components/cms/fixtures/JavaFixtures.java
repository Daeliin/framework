package com.daeliin.components.cms.fixtures;

import com.daeliin.components.cms.library.NewsLibrary;
import com.daeliin.components.cms.sql.BNews;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

import java.time.Instant;
import java.time.ZoneOffset;

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
                        .values("PUBLISHER", "PUBLISHER", "2017-05-05 12:00:00")
                        .values("USER", "USER", "2017-05-05 12:00:00")
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
                        .values(newsRow(NewsLibrary.publishedNewsRow()))
                        .values(newsRow(NewsLibrary.validatedNewsRow()))
                        .values(newsRow(NewsLibrary.draftNewsRow()))
                        .build()
        );
    }

    public static Object[] newsRow(BNews newsRow) {
        return new Object[]{
                newsRow.getId(),
                fixtureDate(newsRow.getCreationDate()),
                newsRow.getAuthorId(),
                newsRow.getTitle(),
                newsRow.getUrlFriendlyTitle(),
                newsRow.getDescription(),
                newsRow.getContent(),
                newsRow.getSource(),
                fixtureDate(newsRow.getPublicationDate()),
                newsRow.getStatus()
        };
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

    public static Object fixtureDate(Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }
}
