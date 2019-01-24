package com.blebail.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static java.util.Objects.requireNonNull;

public final class DbFixture implements BeforeEachCallback {

    private final DbMemory dbMemory;
    private final Operation fixture;
    private final DbSetupTracker dbSetupTracker;

    public DbFixture(DbMemory dbMemory, Operation fixture) {
        this.dbMemory = requireNonNull(dbMemory);
        this.fixture = requireNonNull(fixture);
        this.dbSetupTracker = new DbSetupTracker();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dbMemory.dataSource()), fixture);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    public void readOnly() {
        dbSetupTracker.skipNextLaunch();
    }
}
