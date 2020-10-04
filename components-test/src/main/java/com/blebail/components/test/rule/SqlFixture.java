package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;
import java.util.Optional;

public final class SqlFixture implements BeforeEachCallback {

    private final SqlMemoryDatabase sqlMemoryDatabase;

    private final Operation initialOperation;

    private final DbSetupTracker dbSetupTracker;

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase) {
        this(sqlMemoryDatabase, null);
    }

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase, Operation initialOperation) {
        this.sqlMemoryDatabase = Objects.requireNonNull(sqlMemoryDatabase);
        this.initialOperation = initialOperation;
        this.dbSetupTracker = new DbSetupTracker();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        Optional.ofNullable(initialOperation)
                .map(this::dbSetup)
                .ifPresent(dbSetupTracker::launchIfNecessary);
    }

    public void inject(Operation operation) {
        dbSetup(operation).launch();
    }

    public DbSetup dbSetup(Operation operation) {
        return new DbSetup(new DataSourceDestination(sqlMemoryDatabase.dataSource()), operation);
    }

    /**
     * Avoids initializing the database in the next test.
     * Use only if the test does not make any modification, such as insert, update or delete, to the database.
     */
    public void readOnly() {
        dbSetupTracker.skipNextLaunch();
    }
}
