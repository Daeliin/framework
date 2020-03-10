package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;

public final class SqlFixture implements BeforeEachCallback {

    private final SqlMemoryDatabase sqlMemoryDatabase;

    private final Operation fixture;

    private final DbSetupTracker dbSetupTracker;

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase, Operation fixture) {
        this.sqlMemoryDatabase = Objects.requireNonNull(sqlMemoryDatabase);
        this.fixture = Objects.requireNonNull(fixture);
        this.dbSetupTracker = new DbSetupTracker();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(sqlMemoryDatabase.dataSource()), fixture);

        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    /**
     * Avoids initializing the database in the next test.
     * Use only if the test does not make any modification, such as insert, update or delete, to the database.
     */
    public void readOnly() {
        dbSetupTracker.skipNextLaunch();
    }
}
