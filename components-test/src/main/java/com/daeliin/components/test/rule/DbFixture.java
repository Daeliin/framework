package com.daeliin.components.test.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.rules.ExternalResource;

import static java.util.Objects.requireNonNull;

public final class DbFixture extends ExternalResource  {

    private final DbMemory dbMemory;
    private final Operation fixture;
    private final DbSetupTracker dbSetupTracker;

    public DbFixture(DbMemory dbMemory, Operation fixture) {
        this.dbMemory = requireNonNull(dbMemory);
        this.fixture = requireNonNull(fixture);
        this.dbSetupTracker = new DbSetupTracker();
    }

    @Override
    public void before() throws Throwable {
        super.before();

        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dbMemory.dataSource()), fixture);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }

    public void noRollback() {
        dbSetupTracker.skipNextLaunch();
    }
}
