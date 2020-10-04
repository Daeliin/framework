package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Objects;

public final class SqlFixture implements BeforeEachCallback {

    private final SqlMemoryDatabase sqlMemoryDatabase;

    private final Operation[] initialOperations;

    private final DbSetupTracker dbSetupTracker;

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase) {
        this(sqlMemoryDatabase, new Operation[]{});
    }

    public SqlFixture(SqlMemoryDatabase sqlMemoryDatabase, Operation... initialOperations) {
        this.sqlMemoryDatabase = Objects.requireNonNull(sqlMemoryDatabase);
        this.initialOperations = initialOperations;
        this.dbSetupTracker = new DbSetupTracker();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        if (initialOperations.length > 0) {
            dbSetupTracker.launchIfNecessary(dbSetup(initialOperations));
        }
    }

    private DbSetup dbSetup(Operation... operations) {
        return new DbSetup(new DataSourceDestination(sqlMemoryDatabase.dataSource()), Operations.sequenceOf(operations));
    }

    public void inject(Operation... operations) {
        dbSetup(operations).launch();
    }

    /**
     * Avoids initializing the database in the next test.
     * Use only if the test does not make any modification, such as insert, update or delete, to the database.
     */
    public void readOnly() {
        dbSetupTracker.skipNextLaunch();
    }
}
